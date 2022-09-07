package com.himanshoe.charty.pie

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.pie.config.PieConfig
import com.himanshoe.charty.pie.config.PieConfigDefaults
import com.himanshoe.charty.pie.config.PieData
import kotlin.math.min
import kotlin.math.roundToInt

private const val TotalProgress = 100
private const val TotalAngle = 360
private const val ReflexAnge = 270

@Composable
fun PieChart(
    pieData: List<PieData>,
    modifier: Modifier = Modifier,
    config: PieConfig = PieConfigDefaults.pieConfigDefaults(),
    onSectionClicked: (Float, Float) -> Unit = { _, _ -> }
) {

    if (pieData.isEmpty()) return
    val data = pieData.map { it.data }
    val total = data.sum()
    val proportions = data.map { it.times(TotalProgress).div(total) }
    val angleProgress = proportions.map { TotalAngle.times(it).div(TotalProgress) }

    val currentProgressSize = mutableListOf<Float>().apply {
        add(angleProgress.first())
    }

    (1 until angleProgress.size).forEach { angle ->
        currentProgressSize.add(angleProgress[angle].plus(currentProgressSize[angle.minus(1)]))
    }

    val currentPie = remember { mutableStateOf(-1) }

    var startAngle = ReflexAnge.toFloat()

    BoxWithConstraints(modifier = modifier) {

        val sideSize = min(constraints.maxWidth, constraints.maxHeight)
        val padding = (sideSize.times(if (config.isDonut) 30 else 5)).div(80f)

        val pathPortion = remember { Animatable(initialValue = 0f) }
        LaunchedEffect(key1 = true) {
            pathPortion.animateTo(
                1f, animationSpec = tween(1000)
            )
        }

        val size = Size(sideSize.toFloat().minus(padding), sideSize.toFloat().minus(padding))

        Canvas(
            modifier = Modifier
                .width(sideSize.dp)
                .height(sideSize.dp)
                .handlePiePortionClick(
                    sideSize,
                    config.expandDonutOnClick,
                    currentProgressSize,
                    currentPie
                ) {
                    onSectionClicked(proportions[it], data[it])
                }
        ) {

            angleProgress.forEachIndexed { index, arcProgress ->
                drawPie(
                    pieData[index].color,
                    startAngle,
                    arcProgress * pathPortion.value,
                    size,
                    padding = padding,
                    isDonut = config.isDonut,
                    isActive = currentPie.value == index
                )
                startAngle += arcProgress
            }

            if (currentPie.value != -1 && config.isDonut) {
                drawPieSection(proportions, currentPie.value, config.textColor, sideSize)
            }
        }
    }
}

fun DrawScope.drawPieSection(
    proportions: List<Float>,
    currentPieValue: Int,
    percentColor: Color,
    sideSize: Int
) {
    drawContext.canvas.nativeCanvas.apply {
        val fontSize = size.width.div(20).toDp().toPx()

        drawText(
            "${proportions[currentPieValue].roundToInt()}",
            (sideSize.div(2)).plus(fontSize.div(4)), (sideSize.div(2)).plus(fontSize.div(3)),
            Paint().apply {
                color = percentColor.toArgb()
                textSize = fontSize
                textAlign = Paint.Align.CENTER
            }
        )
    }
}

@SuppressLint("UnnecessaryComposedModifier")
private fun Modifier.handlePiePortionClick(
    sideSize: Int,
    isExpandable: Boolean,
    currentProgressSize: MutableList<Float>,
    currentPie: MutableState<Int>,
    onIndexSelected: (Int) -> Unit
): Modifier = composed {
    if (isExpandable) {
        pointerInput(true) {
            detectTapGestures { offset ->
                val clickedAngle = convertTouchEventPointToAngle(
                    sideSize.toFloat(),
                    sideSize.toFloat(),
                    offset.x,
                    offset.y
                )
                currentProgressSize.forEachIndexed { index, item ->
                    if (clickedAngle <= item) {
                        if (currentPie.value != index) {
                            currentPie.value = index
                        }
                        onIndexSelected(currentPie.value)
                        return@detectTapGestures
                    }
                }
            }
        }
    } else {
        Modifier
    }
}

private fun DrawScope.drawPie(
    color: Color,
    startAngle: Float,
    arcProgress: Float,
    size: Size,
    padding: Float,
    isDonut: Boolean = false,
    isActive: Boolean = false
): Path {

    return Path().apply {
        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = arcProgress,
            useCenter = !isDonut,
            size = size,
            style = if (isDonut)
                Stroke(
                    width = if (isActive) size.width.div(1.5F) else size.width.div(2),
                ) else Fill,

            topLeft = Offset(padding.div(2), padding.div(2))
        )
    }
}

private fun convertTouchEventPointToAngle(
    width: Float,
    height: Float,
    xPos: Float,
    yPos: Float
): Double {
    val x = xPos.minus(width.times(0.5f))
    val y = yPos.minus(height.times(0.5f))

    var angle = Math.toDegrees(kotlin.math.atan2(y.toDouble(), x.toDouble()).plus(Math.PI.div(2)))
    angle = if (angle < 0) angle.plus(TotalAngle) else angle
    return angle
}

package com.himanshoe.charty.pie

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun PieChart(
    modifier: Modifier,
    data: List<Float>,
    colors: List<Color>,
    isDonut: Boolean = false,
    percentColor: Color = Color.Black
) {

    if (data.isEmpty() || data.size != colors.size) return

    val total = data.sum()
    val proportions = data.map {
        it * 100 / total
    }
    val angleProgress = proportions.map {
        360 * it / 100
    }

    val currentProgressSize = mutableListOf<Float>().apply {
        add(angleProgress.first())
    }

    (1 until angleProgress.size)
        .forEach { x ->
            currentProgressSize.add(angleProgress[x].plus(currentProgressSize[x.minus(1)]))
        }

    var currentPie by remember {
        mutableStateOf(-1)
    }

    var startAngle = 270f

    BoxWithConstraints(modifier = modifier) {

        val sideSize = min(constraints.maxWidth, constraints.maxHeight)
        val padding = (sideSize * if (isDonut) 30 else 20) / 100f

        val pathPortion = remember {
            Animatable(initialValue = 0f)
        }
        LaunchedEffect(key1 = true) {
            pathPortion.animateTo(
                1f, animationSpec = tween(1000)
            )
        }

        val size = Size(sideSize.toFloat() - padding, sideSize.toFloat() - padding)

        androidx.compose.foundation.Canvas(
            modifier = Modifier
                .width(sideSize.dp)
                .height(sideSize.dp)
                .pointerInput(true) {

                    if (!isDonut)
                        return@pointerInput

                    detectTapGestures {
                        val clickedAngle = convertTouchEventPointToAngle(
                            sideSize.toFloat(),
                            sideSize.toFloat(),
                            it.x,
                            it.y
                        )
                        currentProgressSize.forEachIndexed { index, item ->
                            if (clickedAngle <= item) {
                                if (currentPie != index)
                                    currentPie = index

                                return@detectTapGestures
                            }
                        }
                    }
                }
        ) {

            angleProgress.forEachIndexed { index, arcProgress ->
                drawPie(
                    colors[index],
                    startAngle,
                    arcProgress * pathPortion.value,
                    size,
                    padding = padding,
                    isDonut = isDonut,
                    isActive = currentPie == index
                )
                startAngle += arcProgress
            }

            if (currentPie != -1)
                drawContext.canvas.nativeCanvas.apply {
                    val fontSize = 600.toDp().toPx()
                    drawText(
                        "${proportions[currentPie].roundToInt()}%",
                        (sideSize / 2) + fontSize / 4, (sideSize / 2) + fontSize / 3,
                        Paint().apply {
                            color = percentColor.toArgb()
                            textSize = fontSize
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
        }
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
            style = if (isDonut) Stroke(
                width = if (isActive) 120f else 100f,
            ) else Fill,

            topLeft = Offset(padding / 2, padding / 2)
        )
    }
}

private fun convertTouchEventPointToAngle(
    width: Float,
    height: Float,
    xPos: Float,
    yPos: Float
): Double {
    val x = xPos - (width * 0.5f)
    val y = yPos - (height * 0.5f)

    var angle = Math.toDegrees(kotlin.math.atan2(y.toDouble(), x.toDouble()) + Math.PI / 2)
    angle = if (angle < 0) angle + 360 else angle
    return angle
}

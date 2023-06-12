package com.himanshoe.charty.circle

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.util.fastMap
import com.himanshoe.charty.circle.config.CircleChartConfig
import com.himanshoe.charty.circle.config.CircleChartLabelTextConfig
import com.himanshoe.charty.circle.config.CircleConfigDefaults
import com.himanshoe.charty.circle.model.CircleData
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.maxYValue

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CircleChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    canAnimate: Boolean = true,
    textLabelTextConfig: CircleChartLabelTextConfig = CircleConfigDefaults.defaultTextLabelConfig(),
    config: CircleChartConfig = CircleConfigDefaults.circleConfigDefaults(),
) {
    val maxYValueState = rememberSaveable { mutableStateOf(dataCollection.maxYValue()) }
    val maxYValue = maxYValueState.value
    val angleFactor = if (config.maxValue != null) 360.div(config.maxValue) else 360.div(maxYValue)

    val animatedFactor = remember {
        Animatable(initialValue = 0f)
    }

    var chartWidth by remember { mutableStateOf(0F) }
    var chartHeight by remember { mutableStateOf(0F) }

    LaunchedEffect(Unit) {
        animatedFactor.animateTo(
            targetValue = angleFactor,
            animationSpec = tween(1000)
        )
    }
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { size ->
                chartWidth = size.width.toFloat()
                chartHeight = size.height.toFloat()
            }
    ) {
        val scaleFactor = chartWidth.div(dataCollection.data.count())
        val sizeArc = size.div(scaleFactor)
        val factor = if (canAnimate) animatedFactor.value else angleFactor

        dataCollection.data.fastForEachIndexed { index, circleData ->
            if (circleData is CircleData) {
                val arcWidth = sizeArc.width.plus(index.times(scaleFactor))
                val arcHeight = sizeArc.height.plus(index.times(scaleFactor))

                drawArc(
                    color = circleData.color,
                    startAngle = config.startAngle.angle,
                    sweepAngle = factor.times(circleData.yValue),
                    topLeft = Offset(
                        (chartWidth - arcWidth).div(2f),
                        (chartHeight - arcHeight).div(2f)
                    ),
                    useCenter = false,
                    style = Stroke(width = scaleFactor.div(2.5F), cap = StrokeCap.Round),
                    size = Size(arcWidth, arcHeight)
                )
            }
        }
    }
    if (config.showLabel) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            dataCollection.data.fastMap {
                if (it is CircleData) {
                    Box(
                        Modifier
                            .size(textLabelTextConfig.indicatorSize)
                            .clip(CircleShape)
                            .align(Alignment.CenterVertically)
                            .background(it.color)
                    )
                    Text(
                        text = it.xValue.toString(),
                        fontSize = textLabelTextConfig.textSize,
                        fontStyle = textLabelTextConfig.fontStyle,
                        fontWeight = textLabelTextConfig.fontWeight,
                        fontFamily = textLabelTextConfig.fontFamily,
                        maxLines = textLabelTextConfig.maxLine,
                        overflow = textLabelTextConfig.overflow,
                        modifier = Modifier.padding(
                            end = 8.dp,
                            start = 4.dp
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CircleChartScreen() {
    val circleData = listOf(
        CircleData(10F, 235F, color = Color(0xFFfafa6e)),
        CircleData(10F, 135F, color = Color(0xFFc4ec74)),
        CircleData(10F, 315F, color = Color(0xFF92dc7e)),
        CircleData(20F, 50F, color = Color(0xFF64c987)),
        CircleData(30F, 315F, color = Color(0xFF39b48e))
    )
    CircleChart(
        modifier = Modifier
            .scale(1f)
            .size(400.dp)
            .padding(20.dp),
        dataCollection = ChartDataCollection(circleData),
    )
}

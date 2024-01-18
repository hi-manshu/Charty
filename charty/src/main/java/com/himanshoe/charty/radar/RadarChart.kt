package com.himanshoe.charty.radar

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import android.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.config.AxisConfig
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.maxYValue
import com.himanshoe.charty.common.minYValue
import com.himanshoe.charty.radar.config.RadarCharDefaults
import com.himanshoe.charty.radar.config.RadarChartColors
import com.himanshoe.charty.radar.config.RadarConfig
import com.himanshoe.charty.radar.model.RadarData
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RadarChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    radarConfig: RadarConfig = RadarCharDefaults.defaultConfig(),
    chartColors: RadarChartColors = RadarCharDefaults.defaultColor(),
    radiusScale: Float = 0.02f,
) {
    val maxYValue by remember { mutableStateOf(dataCollection.maxYValue()) }
    val minYValue by remember { mutableStateOf(dataCollection.minYValue()) }
    val diff = abs(maxYValue * 0.1F)
    val dataRange = (minYValue - diff)..(maxYValue + diff)
    val partitionAngle = (2 * Math.PI / dataCollection.data.size ).toFloat()

    Canvas(
        modifier = modifier
            .drawBehind {
                drawRadarAxis(
                    dataCollection = dataCollection,
                    dataRange = dataRange,
                    radius = size.minDimension / 3,
                    center = PointF(size.width/2, size.height/2),
                    axisConfig = axisConfig,
                )
            }
    ){
        val radius = size.minDimension / 3
        val centerX = size.width / 2
        val centerY = size.height / 2

        val scaleFactor = radius / (dataRange.endInclusive - dataRange.start)

        val lineColor = Brush.linearGradient(chartColors.lineColor)
        val dotColor = Brush.linearGradient(chartColors.dotColor)

        val radarPolygonVertices = mutableListOf<PointF>()

        Path().apply {
            dataCollection.data.fastForEachIndexed { index, chartData ->
                val angle = index * partitionAngle
                val x = centerX + scaleFactor * (chartData.yValue - dataRange.start) * cos(angle)
                val y = centerY + scaleFactor * (chartData.yValue - dataRange.start) * sin(angle)
                if (index == 0){
                    moveTo(x, y)
                } else {
                    lineTo(x, y)
                }

                radarPolygonVertices.add(PointF(x,y))
            }
            close()

            drawPath(
                path = this,
                brush = lineColor,
                style = Stroke(width = radarConfig.strokeSize)
            )
            if (radarConfig.fillPolygon) {
                drawPath(
                    path = this,
                    brush = Brush.linearGradient(chartColors.fillColor),
                )
            }
        }


        if (radarConfig.hasDotMarker) {
            radarPolygonVertices.fastForEach {
                drawCircle(
                    brush = dotColor,
                    radius = radiusScale * size.width,
                    center = Offset(it.x, it.y)
                )
            }
        }
    }
}

private fun DrawScope.drawRadarAxis(
    dataCollection: ChartDataCollection,
    dataRange: ClosedFloatingPointRange<Float>,
    radius: Float,
    center: PointF,
    axisConfig: AxisConfig,
) {
    val partitionAngle = (2 * Math.PI / dataCollection.data.size).toFloat()

    val axisPolygons = if (axisConfig.showGridLines) {
        listOf(Path(), Path(), Path(), Path())
    } else {
        emptyList()
    }

    for (spokeIndex in 0 until dataCollection.data.size){
        val angle = spokeIndex * partitionAngle

        if (axisConfig.showAxes) {
            drawLine(
                start = Offset(center.x, center.y),
                end = Offset(center.x + radius * cos(angle), center.y + radius * sin(angle)),
                color = axisConfig.axisColor,
                strokeWidth = axisConfig.axisStroke
            )
        }

        drawContext.canvas.nativeCanvas.drawText(
            dataCollection.data[spokeIndex].xValue.toString(),
            center.x + (radius + 30F) * cos(angle),
            center.y + (radius + 30F) * sin(angle),
            Paint().apply {
                this.color = axisConfig.axisColor.toArgb()
                this.textSize = size.width / 30
                this.textAlign = if (angle > Math.PI/2 && angle < 3*Math.PI/2) {
                    Paint.Align.RIGHT
                } else {
                    Paint.Align.LEFT
                }
            }
        )

        axisPolygons.fastForEachIndexed { i, path ->
            val scale = 1F - 0.25F * i
            if (spokeIndex == 0){
                path.moveTo(center.x + scale * radius * cos(angle), center.y + scale * radius * sin(angle))
            } else {
                path.lineTo(center.x + scale * radius * cos(angle), center.y + scale * radius * sin(angle))
            }
        }
    }

    if (axisConfig.showGridLines) {
        axisPolygons.fastForEach {
            it.close()
            drawPath(
                path = it,
                color = axisConfig.gridColor,
                style = Stroke(width = axisConfig.axisStroke)
            )
        }
    }

    if (axisConfig.showGridLabel) {
        for (i in axisPolygons.indices) {
            val scale = 1F - 0.25F * i
            drawContext.canvas.nativeCanvas.drawText(
                (dataRange.start + (dataRange.endInclusive - dataRange.start) * scale).toString(),
                center.x + scale * radius * cos(partitionAngle / 2),
                center.y + scale * radius * sin(partitionAngle / 2),
                Paint().apply {
                    this.color = axisConfig.gridColor.toArgb()
                    this.textSize = size.width / 30
                    this.textAlign = Paint.Align.CENTER
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RadarChartPreview() {
    RadarChart(
        dataCollection = ChartDataCollection(listOf(
            RadarData(30f, "AAAAAA"),
            RadarData(25f, "BBBBBB"),
            RadarData(20f, "CCCCCC"),
            RadarData(15f, "DDDDDD"),
            RadarData(10f, "EEEEEE"),
        )),
        modifier = Modifier.size(350.dp),
        axisConfig = ChartDefaults.axisConfigDefaults(),
        radarConfig = RadarCharDefaults.defaultConfig().copy(
            fillPolygon = true
        )
    )
}
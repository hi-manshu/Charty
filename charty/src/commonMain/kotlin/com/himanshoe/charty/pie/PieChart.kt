package com.himanshoe.charty.pie

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.util.fastMap
import com.himanshoe.charty.pie.model.PieChartData
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

internal const val COMPLETE_CIRCLE_DEGREE = 360
internal const val STRAIGHT_ANGLE = 180

/**
 * Composable function to draw a Pie Chart.
 *
 * @param data Lambda function that returns a list of PieChartData representing the slices of the pie chart.
 * @param modifier optional Modifier to be applied to the PieChart.
 * @param isDonutChart Boolean indicating if the chart should be a donut chart.
 * @param onPieChartSliceClick Lambda function to be called when a slice of the pie chart is clicked.
 */
@Composable
fun PieChart(
    data: () -> List<PieChartData>,
    modifier: Modifier = Modifier,
    isDonutChart: Boolean = false,
    onPieChartSliceClick: (PieChartData) -> Unit = {}
) {
    PieChartContent(
        data = data,
        isDonutChart = isDonutChart,
        modifier = modifier,
        onPieChartSliceClick = onPieChartSliceClick,
    )
}

@Composable
private fun PieChartContent(
    data: () -> List<PieChartData>,
    isDonutChart: Boolean = false,
    modifier: Modifier = Modifier,
    onPieChartSliceClick: (PieChartData) -> Unit = {}
) {
    val pieData = data()
    var selectedSlice by remember { mutableStateOf(-1) }
    val totalValue = remember(pieData) { pieData.sumOf { it.value.toDouble() }.toFloat() }
    val proportions = remember(pieData) { pieData.fastMap { it.value / totalValue } }
    val angles = remember(proportions) { proportions.fastMap { 360 * it } }
    val textMeasurer = rememberTextMeasurer()
    var startAngle = 0f

    Canvas(
        modifier = modifier
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    var currentStartAngle = 0f
                    val clickedAngle = (
                            atan2(
                                y = offset.y - size.height / 2,
                                x = offset.x - size.width / 2
                            ) * STRAIGHT_ANGLE / PI + COMPLETE_CIRCLE_DEGREE
                            ) % COMPLETE_CIRCLE_DEGREE
                    angles.fastForEachIndexed { index, sweepAngle ->
                        if (clickedAngle in currentStartAngle..(currentStartAngle + sweepAngle)) {
                            selectedSlice = index
                            onPieChartSliceClick(pieData[index])
                        }
                        currentStartAngle += sweepAngle
                    }
                }
            }
    ) {
        val radius = size.minDimension / 2
        val strokeWidth = radius / 3
        val center = Offset(size.width / 2, size.height / 2)

        angles.fastForEachIndexed { index, sweepAngle ->
            val scale = if (index == selectedSlice) 1.05f else 1.0f
            val scaledRadius = radius * scale
            val angle = (startAngle + sweepAngle / 2) / STRAIGHT_ANGLE * PI
            val sliceCenter = Offset(
                center.x + (scaledRadius - radius) * cos(angle).toFloat(),
                center.y + (scaledRadius - radius) * sin(angle).toFloat()
            )

            // Draw the outer arc with specified stroke width
            drawArc(
                brush = Brush.linearGradient(pieData[index].color.value),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = !isDonutChart,
                size = Size(scaledRadius * 2, scaledRadius * 2),
                topLeft = Offset(sliceCenter.x - scaledRadius, sliceCenter.y - scaledRadius),
                style = if (isDonutChart) Stroke(width = strokeWidth) else Fill
            )

            if (!isDonutChart) {
                val labelAngle = (startAngle + sweepAngle / 2) / STRAIGHT_ANGLE * PI
                val labelRadius = (radius + strokeWidth) / 2
                val labelX = center.x + labelRadius * cos(labelAngle).toFloat()
                val labelY = center.y + labelRadius * sin(labelAngle).toFloat()
                val fontSize = if (index == selectedSlice) 16.sp else 12.sp
                val textLayoutResult = textMeasurer.measure(
                    text = pieData[index].label,
                    style = TextStyle(fontSize = fontSize),
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                )
                drawText(
                    textLayoutResult = textLayoutResult,
                    brush = Brush.linearGradient(pieData[index].labelColor.value),
                    topLeft = Offset(
                        x = labelX - textLayoutResult.size.width / 2,
                        y = labelY - textLayoutResult.size.height / 2,
                    ),
                )
            }
            startAngle += sweepAngle
        }
    }
}

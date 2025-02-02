package com.himanshoe.charty.pie

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.asSolidChartColor
import com.himanshoe.charty.pie.model.PieChartData
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

private const val COMPLETE_CIRCLE_DEGREE = 360
private const val STRAIGHT_ANGLE = 180

/**
 * Composable function to draw a Pie Chart.
 *
 * @param data List of PieChartData representing the slices of the pie chart.
 * @param isDonutChart Boolean indicating if the chart should be a donut chart.
 * @param modifier Modifier to be applied to the Canvas.
 * @param backgroundColor Background color for the donut chart.
 * @param onPieChartSliceClick Lambda function to be called when a slice of the pie chart is clicked.
 */
@Composable
fun PieChart(
    data: () -> List<PieChartData>,
    modifier: Modifier = Modifier,
    isDonutChart: Boolean = false,
    backgroundColor: ChartColor = Color.Unspecified.asSolidChartColor(),
    onPieChartSliceClick: (PieChartData) -> Unit = {}
) {
    PieChartContent(
        data = data,
        isDonutChart = isDonutChart,
        modifier = modifier,
        onPieChartSliceClick = onPieChartSliceClick,
        backgroundColor = backgroundColor
    )
}

@Composable
private fun PieChartContent(
    data: () -> List<PieChartData>,
    isDonutChart: Boolean = false,
    modifier: Modifier = Modifier,
    backgroundColor: ChartColor = Color.Unspecified.asSolidChartColor(),
    onPieChartSliceClick: (PieChartData) -> Unit = {}
) {
    require(!isDonutChart || backgroundColor != Color.Unspecified.asSolidChartColor()) {
        "Background color must be specified when isDonutChart is true"
    }
    var selectedSlice by remember { mutableStateOf(-1) }
    val totalValue = remember(data) { data().sumOf { it.value.toDouble() }.toFloat() }
    val proportions = remember(data) { data().map { it.value / totalValue } }
    val angles = remember(proportions) { proportions.map { 360 * it } }
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier.pointerInput(Unit) {
        detectTapGestures { offset ->
            val clickedAngle = (atan2(
                offset.y - size.height / 2,
                offset.x - size.width / 2
            ) * STRAIGHT_ANGLE / PI + COMPLETE_CIRCLE_DEGREE) % COMPLETE_CIRCLE_DEGREE
            var startAngle = 0f
            angles.fastForEachIndexed { index, sweepAngle ->
                if (clickedAngle in startAngle..(startAngle + sweepAngle)) {
                    selectedSlice = index
                    onPieChartSliceClick(data()[index])
                }
                startAngle += sweepAngle
            }
        }
    }) {
        val radius = size.minDimension / 2
        val innerRadius = radius * 2 / 3
        val center = Offset(size.width / 2, size.height / 2)

        var startAngle = 0f
        angles.fastForEachIndexed { index, sweepAngle ->
            val scale = if (index == selectedSlice) 1.05f else 1.0f
            val scaledRadius = radius * scale
            val scaledInnerRadius = innerRadius * scale
            val angle = (startAngle + sweepAngle / 2) / STRAIGHT_ANGLE * PI
            val sliceCenter = Offset(
                center.x + (scaledRadius - radius) * cos(angle).toFloat(),
                center.y + (scaledRadius - radius) * sin(angle).toFloat()
            )

            // Draw the outer arc
            drawArc(
                brush = Brush.linearGradient(data()[index].color.value),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = Size(scaledRadius * 2, scaledRadius * 2),
                topLeft = Offset(sliceCenter.x - scaledRadius, sliceCenter.y - scaledRadius)
            )

            if (isDonutChart) {
                drawArc(
                    brush = Brush.linearGradient(backgroundColor.value),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    size = Size(scaledInnerRadius * 2, scaledInnerRadius * 2),
                    topLeft = Offset(
                        sliceCenter.x - scaledInnerRadius,
                        sliceCenter.y - scaledInnerRadius
                    )
                )
            }
            if (!isDonutChart) {
                val labelAngle = (startAngle + sweepAngle / 2) / STRAIGHT_ANGLE * PI
                val labelRadius = (radius + innerRadius) / 2
                val labelX = center.x + labelRadius * cos(labelAngle).toFloat()
                val labelY = center.y + labelRadius * sin(labelAngle).toFloat()
                val fontSize = if (index == selectedSlice) 16.sp else 12.sp
                val textLayoutResult = textMeasurer.measure(
                    text = data()[index].label,
                    style = TextStyle(fontSize = fontSize),
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                )
                drawText(
                    textLayoutResult = textLayoutResult,
                    brush = Brush.linearGradient(data()[index].labelColor.value),
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
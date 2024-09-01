package com.himanshoe.charty.util

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.circle.CircleChart
import com.himanshoe.charty.circle.model.CircleData
import com.himanshoe.charty.common.toChartDataCollection
import com.himanshoe.charty.gauge.GaugeChart
import com.himanshoe.charty.line.CurveLineChart
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.pie.PieChart
import com.himanshoe.charty.pie.model.PieData
import com.himanshoe.charty.point.PointChart
import com.himanshoe.charty.point.model.PointData

/*
    Collection of all chart previews. This previews are here because KMM does not have support
    for previews in the common module.
*/
@Preview
@Composable
internal fun CircleChartScreen() {
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
        dataCollection = circleData.toChartDataCollection(),
    )
}

@Preview
@Composable
internal fun GaugeChartPreview() {
    val percentValue = 100
    GaugeChart(percentValue = percentValue)
}

@Preview
@Composable
internal fun LineChartPreview(modifier: Modifier = Modifier) {
    Column(modifier) {
        CurveLineChart(
            dataCollection = generateMockLineData().toChartDataCollection(),
            modifier = Modifier
                .size(450.dp),
        )
    }
}

internal fun generateMockLineData(): List<LineData> {
    return listOf(
        LineData(0F, "Jan"),
        LineData(10F, "Feb"),
        LineData(05F, "Mar"),
        LineData(50F, "Apr"),
        LineData(03F, "June"),
        LineData(9F, "July"),
        LineData(40F, "Aug"),
        LineData(60F, "Sept"),
        LineData(33F, "Oct"),
        LineData(11F, "Nov"),
        LineData(27F, "Dec"),
        LineData(10F, "Jan"),
        LineData(13F, "Oct"),
        LineData(-10F, "Nov"),
        LineData(0F, "Dec"),
        LineData(10F, "Jan"),
    )
}

@Preview
@Composable
internal fun PieChartPreview() {
    val data = listOf(
        PieData(30f, "Category A", Color.Blue),
        PieData(20f, "Category B", Color.Red),
        PieData(10f, "Category C", Color.Green),
        PieData(10f, "Category C", Color.Black),
    )
    PieChart(
        dataCollection = data.toChartDataCollection(),
        modifier = Modifier.wrapContentSize()
    )
}

@Preview
@Composable
internal fun PointChartPreview(modifier: Modifier = Modifier) {
    Column(modifier) {
        PointChart(
            dataCollection = generateMockPointData().toChartDataCollection(),
            modifier = Modifier
                .size(450.dp),
            contentColor = Color.Red,
        )
    }
}

internal fun generateMockPointData(): List<PointData> {
    return listOf(
        PointData(-10F, "Jan"),
        PointData(10F, "Feb"),
        PointData(05F, "Mar"),
        PointData(50F, "Apr"),
        PointData(03F, "June"),
        PointData(9F, "July"),
        PointData(40F, "Aug"),
        PointData(60F, "Sept"),
        PointData(33F, "Oct"),
        PointData(11F, "Nov"),
        PointData(27F, "Dec"),
        PointData(10F, "Jan"),
        PointData(73F, "Oct"),
        PointData(-20F, "Nov"),
        PointData(0F, "Dec"),
        PointData(10F, "Jan"),
    )
}

/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.pie

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.config.ChartyLabelTextConfig
import com.himanshoe.charty.common.toChartDataCollection
import com.himanshoe.charty.pie.config.PieChartConfig
import com.himanshoe.charty.pie.config.PieChartDefaults
import com.himanshoe.charty.pie.model.PieData

/**
 * Displays a pie chart based on the provided data collection.
 *
 * @param dataCollection The collection of data points for the pie chart.
 * @param modifier The modifier for styling the pie chart.
 * @param textLabelTextConfig The configuration for the text labels in the pie chart.
 * @param pieChartConfig The configuration for the pie chart.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PieChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    textLabelTextConfig: ChartyLabelTextConfig = ChartDefaults.defaultTextLabelConfig(),
    pieChartConfig: PieChartConfig = PieChartDefaults.defaultConfig(),
) {
    val pieChartData = dataCollection.data.fastMap { it.yValue }
    val total = pieChartData.sum()

    var startAngle = pieChartConfig.startAngle.angle

    Column(modifier) {
        Canvas(
            modifier = Modifier
                .aspectRatio(1F)
        ) {
            val radius = size.minDimension / 3
            val centerX = size.width / 2
            val centerY = size.height / 2
            val holeRadius =
                if (pieChartConfig.donut) radius * 0.4f else 0f // Adjust the hole radius based on donut config

            dataCollection.data.fastForEach { value ->
                if (value is PieData) {
                    val sweepAngle = (value.yValue / total) * 360
                    if (pieChartConfig.donut.not()) {
                        drawArc(
                            color = value.color,
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = true,
                        )
                    } else {
                        drawArc(
                            color = value.color,
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            topLeft = Offset(centerX - radius, centerY - radius),
                            size = Size(radius * 2, radius * 2),
                            style = Stroke(width = radius - holeRadius)
                        )
                    }
                    startAngle += sweepAngle
                }
            }
        }
        if (pieChartConfig.showLabel) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                dataCollection.data.fastMap {
                    if (it is PieData) {
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
}

@Composable
@Preview
private fun PieChartPreview() {
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

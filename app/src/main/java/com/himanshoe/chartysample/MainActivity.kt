/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.chartysample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.area.AreaChart
import com.himanshoe.charty.area.config.AreaChartColors
import com.himanshoe.charty.area.model.AreaData
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.config.BarChartColors
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.bubble.BubbleChart
import com.himanshoe.charty.bubble.model.BubbleData
import com.himanshoe.charty.candle.CandleStickChart
import com.himanshoe.charty.candle.config.CandleStickDefaults
import com.himanshoe.charty.candle.model.CandleData
import com.himanshoe.charty.circle.CircleChart
import com.himanshoe.charty.circle.model.CircleData
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.ComposeList
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.toComposeList
import com.himanshoe.charty.gauge.GaugeChart
import com.himanshoe.charty.group.GroupedBarChart
import com.himanshoe.charty.group.config.GroupBarChartColors
import com.himanshoe.charty.group.model.GroupBarData
import com.himanshoe.charty.line.CurveLineChart
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.config.CurvedLineChartColors
import com.himanshoe.charty.line.config.CurvedLineChartDefaults
import com.himanshoe.charty.line.config.LineChartDefaults
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.pie.PieChart
import com.himanshoe.charty.pie.config.PieChartDefaults
import com.himanshoe.charty.pie.model.PieData
import com.himanshoe.charty.point.PointChart
import com.himanshoe.charty.stacked.StackedBarChart
import com.himanshoe.charty.stacked.config.StackBarData
import com.himanshoe.charty.stacked.config.StackedBarChartColors
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContent {
                ChartContent(Modifier.fillMaxSize())
            }
        }
    }

    private fun generateMockBubbleData(count: Int = 10): List<BubbleData> {
        val mockData = mutableListOf<BubbleData>()
        for (i in 1..count) {
            val xValue = "X$i"
            val yValue = Random.nextFloat() * 100
            val volumeSize = Random.nextFloat() * 100
            val bubbleData = BubbleData(xValue, yValue, volumeSize)
            mockData.add(bubbleData)
        }
        return mockData
    }

    private val data = listOf(
        PieData(30f, "Category A", color = Color(0xffed625d)),
        PieData(20f, "Category B", color = Color(0xfff79f88)),
        PieData(13f, "Category C", color = Color(0xFF43A047)),
        PieData(10f, "Category D", color = Color(0xFF93A047)),
    )
    private val circleData = listOf(
        CircleData(30f, "Category A", color = Color(0xffed625d)),
        CircleData(20f, "Category B", color = Color(0xfff79f88)),
        CircleData(13f, "Category C", color = Color(0xFF43A047)),
        CircleData(10f, "Category D", color = Color(0xFF93A047)),
    )
    private val bardata = listOf(
        BarData(10f, "Category A", color = Color(0xffed625d)),
        BarData(20f, "Category B", color = Color(0xffed125d)),
        BarData(50f, "Category C", color = Color(0xffed225d)),
        BarData(40f, "Category D", color = Color(0xffed325d)),
        BarData(23f, "Category E", color = Color(0xffed425d)),
        BarData(35F, "Category F", color = Color(0xffed525d)),
        BarData(20f, "Category K", color = Color(0xffed615d)),
        BarData(50f, "Category L", color = Color(0xffed625d)),
    )
    private val areaData = listOf(
        AreaData(
            points = listOf(0.5f, 0.8f, 0.6f, 0.9f, 0.7f, 0.4f),
            xValue = "Item 1",
            color = Color.Yellow
        ),
        AreaData(
            xValue = "Item 2",
            points = listOf(0.33f, 0.6f, 0.93f, 0.7f, 0.9f, 1.5f),
            color = Color.DarkGray
        ),
        AreaData(
            xValue = "Item 3",
            points = listOf(0.3f, 0.6f, 0.4f, 0.7f, 0.9f, 0.3f),
            color = Color.Red
        ),
    )

    private val chartColors = listOf(
        Color(0xffed625d),
        Color(0xfff79f88),
        Color(0xFF43A047)
    )

    private val backgroundColors = listOf(
        Color.White,
        Color.White,
    )

    private val groupData = listOf(
        GroupBarData(
            label = "Group 1",
            dataPoints = listOf(10f, 15f, 8f),
            chartColors
        ),
        GroupBarData(
            label = "Group 2",
            dataPoints = listOf(12f, 6f, 14f),
            chartColors
        ),
        GroupBarData(
            label = "Group 3",
            dataPoints = listOf(5f, 9f, 11f),
            chartColors
        ),
        GroupBarData(
            label = "Group 4",
            dataPoints = listOf(5f, 9f, 11f),
            chartColors
        ),
        GroupBarData(
            label = "Group 5",
            dataPoints = listOf(12f, 6f, 14f),
            chartColors
        ),
        GroupBarData(
            label = "Group 7",
            dataPoints = listOf(5f, 9f, 11f),
            chartColors
        ),
        GroupBarData(
            label = "Group 8",
            dataPoints = listOf(1f, 9f, 11f),
            chartColors
        )
    )

    @Composable
    fun StackedBarChartDemo(backgroundColors: List<Color>) {

        val stackBarData = ComposeList(
            listOf(
                StackBarData(dataPoints = listOf(3f, 20F, 30f), colors = chartColors, label = "1"),
                StackBarData(dataPoints = listOf(9f, 25f, 35f), colors = chartColors, label = "2"),
                StackBarData(dataPoints = listOf(10f, 25f, 35f), colors = chartColors, label = "3"),
                StackBarData(dataPoints = listOf(1f, 25f, 3f), colors = chartColors, label = "4"),
                StackBarData(dataPoints = listOf(10f, 25f, 5f), colors = chartColors, label = "5"),
                StackBarData(dataPoints = listOf(10f, 25f, 35f), colors = chartColors, label = "6"),
                StackBarData(dataPoints = listOf(10f, 25f, 35f), colors = chartColors, label = "7"),
                StackBarData(dataPoints = listOf(10f, 25f, 35f), colors = chartColors, label = "8"),
                StackBarData(dataPoints = listOf(10f, 25f, 20f), colors = chartColors, label = "9"),
                StackBarData(
                    dataPoints = listOf(10f, 25f, 35f),
                    colors = chartColors,
                    label = "10"
                ),
                StackBarData(
                    dataPoints = listOf(10f, 25f, 35f),
                    colors = chartColors,
                    label = "11"
                ),
                StackBarData(
                    dataPoints = listOf(10f, 25f, 35f),
                    colors = chartColors,
                    label = "12"
                ),
                StackBarData(
                    dataPoints = listOf(10f, 25f, 35f),
                    colors = chartColors,
                    label = "13"
                ),
                StackBarData(
                    dataPoints = listOf(10f, 25f, 35f),
                    colors = chartColors,
                    label = "14"
                ),
            )
        )
        StackedBarChart(
            stackBarData = stackBarData,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            chartColors = StackedBarChartColors(backgroundColors = backgroundColors),
        )
    }

    @Composable
    fun CandlestickChartExample(backgroundColors: List<Color>) {
        val candleData = listOf(
            CandleData(high = 20f, low = 8f, open = 10f, close = 15f),
            CandleData(high = 22f, low = 16f, open = 18f, close = 20f),
            CandleData(high = 14f, low = 8f, open = 12f, close = 9f),
            CandleData(high = 9f, low = 3f, open = 7f, close = 5f),
            CandleData(high = 10f, low = 4f, open = 6f, close = 8f),
            CandleData(high = 15f, low = 10f, open = 13f, close = 12f),
            CandleData(high = 20f, low = 8f, open = 10f, close = 15f),
            CandleData(high = 22f, low = 16f, open = 18f, close = 20f),
            CandleData(high = 14f, low = 8f, open = 12f, close = 9f),
            CandleData(high = 9f, low = 3f, open = 7f, close = 5f),
            CandleData(high = 10f, low = 4f, open = 6f, close = 8f),
            CandleData(high = 15f, low = 10f, open = 13f, close = 12f),
            CandleData(high = 9f, low = 3f, open = 7f, close = 5f),
            CandleData(high = 10f, low = 4f, open = 6f, close = 8f),
            CandleData(high = 15f, low = 10f, open = 13f, close = 12f),
            CandleData(high = 20f, low = 8f, open = 10f, close = 15f),
            CandleData(high = 22f, low = 16f, open = 18f, close = 20f),
            CandleData(high = 14f, low = 8f, open = 12f, close = 9f),
            CandleData(high = 9f, low = 3f, open = 7f, close = 5f),
            CandleData(high = 10f, low = 4f, open = 6f, close = 8f),
            CandleData(high = 15f, low = 10f, open = 13f, close = 12f),
        )

        CandleStickChart(
            candleData = ComposeList(candleData),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            candleConfig = CandleStickDefaults.defaultCandleStickConfig().copy(
                backgroundColors = backgroundColors,
            ),
        )
    }

    @Composable
    private fun ChartContent(modifier: Modifier = Modifier) {
        LazyColumn(modifier) {
            item {
                BarChart(
                    dataCollection = ChartDataCollection(bardata),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    chartColors = BarChartColors(backgroundColors = backgroundColors),
                )
            }
            item {
                val percentValue = 75
                GaugeChart(
                    percentValue = percentValue,
                    modifier = Modifier.background(brush = Brush.linearGradient(backgroundColors)),
                )
            }
            item {
                StackedBarChartDemo(backgroundColors = backgroundColors)
            }
            item {
                CandlestickChartExample(backgroundColors = backgroundColors)
            }
            item {
                GroupedBarChart(
                    groupBarDataCollection = groupData.toComposeList(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    chartColors = GroupBarChartColors(backgroundColors = backgroundColors),
                )
            }
            item {
                AreaChart(
                    areaData = ComposeList(areaData),
                    modifier = Modifier
                        .size(400.dp),
                    chartColors = AreaChartColors(backgroundColors = backgroundColors),
                )
            }
            item {
                BubbleChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    dataCollection = ChartDataCollection(generateMockBubbleData()),
                    chartColors = CurvedLineChartColors(backgroundColors = backgroundColors),
                )
            }
            item {
                CircleChart(
                    modifier = Modifier
                        .size(400.dp),
                    dataCollection = ChartDataCollection(circleData),
                )
            }
            item {
                PieChart(
                    pieChartConfig = PieChartDefaults.defaultConfig().copy(donut = false),
                    dataCollection = ChartDataCollection(data),
                    modifier = Modifier
                        .wrapContentSize(),
                )
            }
            item {
                PieChart(
                    dataCollection = ChartDataCollection(data),
                    modifier = Modifier
                        .wrapContentSize(),
                )
            }
            item {
                CurveLineChart(
                    dataCollection = ChartDataCollection(generateMockLineDataList()),
                    modifier = Modifier
                        .size(450.dp),
                    chartColors = CurvedLineChartDefaults.defaultColor().copy(
                        backgroundColors = backgroundColors,
                    ),
                )
            }
            item {
                LineChart(
                    dataCollection = ChartDataCollection(generateMockLineDataList()),
                    modifier = Modifier
                        .size(450.dp),
                    chartColors = LineChartDefaults.defaultColor().copy(
                        backgroundColors = backgroundColors,
                    ),
                )
            }
            item {
                PointChart(
                    dataCollection = ChartDataCollection(generateMockLineDataList()),
                    modifier = Modifier
                        .size(450.dp),
                    chartColors = ChartDefaults.colorDefaults().copy(
                        backgroundColors = backgroundColors,
                    ),
                )
            }
        }
    }

    private fun generateMockLineDataList(): List<LineData> {
        return listOf(
            LineData(0F, "Jan"),
            LineData(10F, "Feb"),
            LineData(05F, "Mar"),
            LineData(50F, "Apr"),
            LineData(55F, "May"),
            LineData(03F, "June"),
            LineData(9F, "July"),
            LineData(40F, "Aug"),
            LineData(60F, "Sept"),
            LineData(33F, "Oct"),
            LineData(11F, "Nov"),
        )
    }
}

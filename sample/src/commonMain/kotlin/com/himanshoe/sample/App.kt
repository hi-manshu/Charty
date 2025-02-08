package com.himanshoe.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.ComparisonBarChart
import com.himanshoe.charty.bar.HorizontalBarChart
import com.himanshoe.charty.bar.LineBarChart
import com.himanshoe.charty.bar.LineStackedBarChart
import com.himanshoe.charty.bar.SignalProgressBarChart
import com.himanshoe.charty.bar.StackedBarChart
import com.himanshoe.charty.bar.StorageBar
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.bar.model.ComparisonBarData
import com.himanshoe.charty.bar.model.StackBarData
import com.himanshoe.charty.bar.model.StorageData
import com.himanshoe.charty.circle.CircleChart
import com.himanshoe.charty.circle.SpeedometerProgressBar
import com.himanshoe.charty.circle.model.CircleData
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.common.asGradientChartColor
import com.himanshoe.charty.common.asSolidChartColor
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.MultiLineChart
import com.himanshoe.charty.line.config.LineChartColorConfig
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.line.model.MultiLineData
import com.himanshoe.charty.pie.PieChart
import com.himanshoe.charty.pie.model.PieChartData
import com.himanshoe.charty.point.PointChart
import com.himanshoe.charty.point.model.PointData
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random


@Composable
@Preview
fun App() {
    LazyColumn {
        addStackBarChart()
        addSpeedometerProgressBar()
        addCircleChart()
        addPieChart()
        addComparisonChart()
        addLineChart()
        addMultiLineChart()
        addPointChart()
        addBarChart(null, generateMockBarData(7, false, false))
        addHorizontalBarChart()
        addStorageBarChart()
        addSignalBarChart()
        addLineBarChart(3F, { generateMockBarData(7) })
        addLineBarChart(null, { generateMockBarData(7) })
        addBarChart(2F, generateMockBarData(11))
        addBarChart(null, generateMockBarData(7, false))
    }
}

private fun LazyListScope.addSpeedometerProgressBar() {
    item {
        Box(
            modifier = Modifier.fillParentMaxWidth().size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            SpeedometerProgressBar(
                progress = { 0.50f },
                title = "Temperature",
                modifier = Modifier.size(300.dp),
                trackColor = Color.Gray.copy(alpha = 0.2F).asSolidChartColor(),
                color = ChartColor.Gradient(
                    listOf(
                        Color(0xFF2193b0),
                        Color(0xFF6dd5ed)
                    )
                ),
                progressIndicatorColor = ChartColor.Gradient(
                    listOf(
                        Color(0xFF2193b0),
                        Color(0xFF6dd5ed),
                        Color(0xFF1e88e5),
                        Color(0xFF42a5f5)
                    )
                )
            )
        }
    }
}

private fun LazyListScope.addCircleChart() {
    item {
        val data = remember { { generateMockCircleData() } }
        Box(
            modifier = Modifier.fillParentMaxWidth().size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            CircleChart(
                data = data,
                modifier = Modifier.size(300.dp),
                onCircleClick = { circleData ->
                    println("Clicked on circle with data: $circleData")
                }
            )
        }
    }
}

private fun LazyListScope.addPieChart() {
    val data = listOf(
        PieChartData(
            25f,
            listOf(Color(0xFFFFAFBD), Color(0xFFFFC3A0)).asGradientChartColor(),
            label = "25%"
        ),
        PieChartData(
            35f,
            listOf(Color(0xFFf12711), Color(0xFFf5af19)).asGradientChartColor(),
            label = "35%"
        ),
        PieChartData(
            20f,
            listOf(Color(0xFFbc4e9c), Color(0xFFf80759)).asGradientChartColor(),
            label = "20%"
        ),
        PieChartData(
            10f,
            listOf(Color(0xFF11998e), Color(0xFF38ef7d)).asGradientChartColor(),
            label = "10%"
        ),
        PieChartData(
            10f,
            listOf(Color(0xFF11998e), Color(0xFF385f7d)).asGradientChartColor(),
            label = "10%"
        ),
    )

    item {
        Box(
            modifier = Modifier.fillParentMaxWidth().size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            PieChart(
                isDonutChart = false,
                isHalfPieChart = false,
                backgroundColor = Color.White.asSolidChartColor(),
                data = { data },
                modifier = Modifier.size(300.dp).fillParentMaxWidth().padding(4.dp)
            )
        }
    }
    item {
        Box(
            modifier = Modifier.fillParentMaxWidth().size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            PieChart(
                isDonutChart = true,
                backgroundColor = Color.White.asSolidChartColor(),
                data = { data },
                modifier = Modifier.size(300.dp).fillParentMaxWidth().padding(4.dp)
            )
        }
    }
    item {
        Box(
            modifier = Modifier.fillParentMaxWidth().size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            PieChart(
                isDonutChart = true,
                isHalfPieChart = true,
                backgroundColor = Color.White.asSolidChartColor(),
                data = { data },
                modifier = Modifier.height(150.dp).fillParentMaxWidth().padding(4.dp)
            )
        }
    }
}


private fun LazyListScope.addComparisonChart() {
    item {
        val barColors = listOf(
            ChartColor.Gradient(
                listOf(
                    Color(0xFF2193b0),
                    Color(0xFF6dd5ed)
                )
            ),
            ChartColor.Gradient(
                listOf(
                    Color(0xFFCB356B),
                    Color(0xFFBD3F32),
                )
            ),
            ChartColor.Gradient(
                listOf(
                    Color(0xFFF23222),
                    Color(0xFFff6a00)
                )
            )
        )
        val mockData = listOf(
            ComparisonBarData(
                label = "Category 1", bars = listOf(45f, 70f, 30f), colors = barColors
            ), ComparisonBarData(
                label = "Category 2", bars = listOf(80f, 60f, 90f), colors = barColors
            ), ComparisonBarData(
                label = "Category 3", bars = listOf(40f, 20f, 50f), colors = barColors
            ), ComparisonBarData(
                label = "Category 4", bars = listOf(40f, 20f), colors = barColors.take(2)

            ), ComparisonBarData(
                label = "Category 5", bars = listOf(40f, 20f, 50f), colors = barColors
            )
        )

        ComparisonBarChart(data = { mockData },
            modifier = Modifier.padding(10.dp).fillMaxWidth().height(300.dp),
            onGroupClicked = { index ->
                println("Category $index clicked")
            })
    }

}

private fun LazyListScope.addPointChart() {
    item {
        val mockData = listOf(
            PointData(xValue = "Mon", yValue = 10f),
            PointData(xValue = "Tue", yValue = 20f),
            PointData(xValue = "Wed", yValue = 15f),
            PointData(xValue = "Thu", yValue = 25f),
            PointData(xValue = "Fri", yValue = 30f),
            PointData(xValue = "Mon", yValue = 10f),
            PointData(xValue = "Tue", yValue = 20f),
            PointData(xValue = "Wed", yValue = 15f),
            PointData(xValue = "Thu", yValue = 25f),
            PointData(xValue = "Fri", yValue = 30f),
            PointData(xValue = "Fri", yValue = 30f),
            PointData(xValue = "Mon", yValue = 10f),
            PointData(xValue = "Tue", yValue = 20f),
            PointData(xValue = "Wed", yValue = 15f),
            PointData(xValue = "Thu", yValue = 25f),
            PointData(xValue = "Fri", yValue = 30f),
        )

        PointChart(
            data = { mockData },
            target = 18f,
            modifier = Modifier.padding(10.dp).fillMaxWidth().height(300.dp),
            onPointClick = { index, circleData ->
                println("Clicked on index: $index with data: $circleData")
            },
            labelConfig = LabelConfig.default().copy(showXLabel = true, showYLabel = false),
        )
    }

}

private fun LazyListScope.addMultiLineChart() {
    item {
        val xValues = listOf("Mon", "Tue", "Wed", "Thu", "Fri")
        val yValuesList = listOf(
            listOf(0F, 5F, 2F, 11F, 3F), listOf(10F, 5F, 12F, 11F, 30F)
        )
        val colorConfigs = listOf(
            LineChartColorConfig.default().copy(
                lineColor = ChartColor.Solid(Color.Red), lineFillColor = ChartColor.Gradient(
                    listOf(Color(0xFFCB356B), Color(0xFFBD3F32))
                )
            ), LineChartColorConfig.default().copy(
                lineColor = ChartColor.Solid(Color.Blue), lineFillColor = ChartColor.Gradient(
                    listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
                )
            )
        )
        val mockData = generateMultiLineData(yValuesList, xValues, colorConfigs)

        MultiLineChart(
            data = { mockData },
            modifier = Modifier.padding(10.dp).fillMaxWidth().height(300.dp),
            smoothLineCurve = true,
            showFilledArea = true,
            showLineStroke = true,
            target = 6F,
        )
    }
}

private fun LazyListScope.addLineChart() {
    item {
        LineChart(
            data = {
                listOf(
                    LineData(0F, "Mon"),
                    LineData(5F, "Tue"),
                    LineData(2F, "Wed"),
                    LineData(11F, "Thu"),
                    LineData(1F, "Mon"),
                    LineData(5F, "Tue"),
                    LineData(2F, "Wed"),
                    LineData(11F, "Thu"),
                    LineData(3F, "Fri")
                )
            }, modifier = Modifier.padding(10.dp).fillMaxWidth().height(300.dp)
        )
    }
}

private fun LazyListScope.addHorizontalBarChart() {
    item {
        HorizontalBarChart(
            data = { generateMockBarData(7, useColor = false) },
            modifier = Modifier.fillParentMaxWidth().height(300.dp).padding(all = 20.dp)

        )
    }
    item {
        HorizontalBarChart(
            data = { generateMockBarData(7, useColor = false, hasNegative = false) },
            modifier = Modifier.padding(all = 20.dp).fillParentMaxWidth().height(300.dp)
                .padding(all = 20.dp)

        )
    }
    item {
        HorizontalBarChart(
            data = { generateAllNegativeBarData(7, useColor = false) },
            modifier = Modifier.padding(all = 20.dp).fillParentMaxWidth().height(300.dp)
                .padding(all = 20.dp)

        )
    }
}

private fun LazyListScope.addStorageBarChart() {
    item {
        Column(modifier = Modifier.fillParentMaxWidth().padding(vertical = 4.dp)) {
            val data = generateMockStorageCategories()
            Text(
                text = "Storage Bar Chart",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
            StorageBar(
                data = { data },
                modifier = Modifier.padding(horizontal = 12.dp).fillMaxWidth().height(30.dp)
            )
            Row(
                modifier = Modifier.fillParentMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                data.fastForEach {
                    Box(
                        modifier = Modifier.padding(start = 4.dp).size(10.dp).clip(CircleShape)
                            .background(it.color.value.first()), contentAlignment = Alignment.Center
                    ) {}
                    Text(
                        text = it.name, modifier = Modifier.padding(all = 4.dp)
                    )
                }
            }
        }
    }
}

private fun LazyListScope.addSignalBarChart() {
    item {
        SignalProgressBarChart(
            progress = { 79F },
            modifier = Modifier.padding(all = 12.dp).fillMaxWidth(0.15F).height(300.dp),
            trackColor = ChartColor.Gradient(listOf(Color.Gray, Color.Black)),
            progressColor = ChartColor.Gradient(
                listOf(
                    Color(0xFFffafbd),
                    Color(0xFFffc3a0),
                )
            ),
            gapRatio = 0.1F
        )
    }
}

private fun LazyListScope.addLineBarChart(target: Float?, data: () -> List<BarData>) {
    item {
        LineBarChart(target = target,
            modifier = Modifier.padding(10.dp).fillMaxWidth().height(300.dp),
            data = data,
            onBarClick = { _, barData: BarData -> })
    }
}

private fun LazyListScope.addBarChart(target: Float?, data: List<BarData>) {
    item {
        BarChart(modifier = Modifier.padding(10.dp).fillMaxWidth().height(300.dp),
            target = target,
            data = { data },
            onBarClick = { _, barData -> })
    }
}

private fun LazyListScope.addStackBarChart() {
    item {
        val data = listOf(
            StackBarData(
                label = "Jan",
                values = listOf(100f, 30f, 30f),
                colors = listOf(
                    ChartColor.Solid(Color.Red),
                    ChartColor.Solid(Color.Green),
                    ChartColor.Solid(Color.Blue)
                )
            ),
            StackBarData(
                label = "Feb",
                values = listOf(15f, 25f, 35f),
                colors = listOf(
                    ChartColor.Solid(Color.Red),
                    ChartColor.Solid(Color.Green),
                    ChartColor.Solid(Color.Blue)
                )
            ),
            StackBarData(
                label = "Mar",
                values = listOf(20f, 30f, 40f),
                colors = listOf(
                    ChartColor.Solid(Color.Red),
                    ChartColor.Solid(Color.Green),
                    ChartColor.Solid(Color.Blue)
                )
            ),
            StackBarData(
                label = "Apr",
                values = listOf(25f, 35f, 45f),
                colors = listOf(
                    ChartColor.Solid(Color.Red),
                    ChartColor.Solid(Color.Green),
                    ChartColor.Solid(Color.Blue)
                )
            ),
            StackBarData(
                label = "May",
                values = listOf(30f, 40f, 50f),
                colors = listOf(
                    ChartColor.Solid(Color.Red),
                    ChartColor.Solid(Color.Green),
                    ChartColor.Solid(Color.Blue)
                )
            ),
            StackBarData(
                label = "Jun",
                values = listOf(35f, 45f, 55f),
                colors = listOf(
                    ChartColor.Solid(Color.Red),
                    ChartColor.Solid(Color.Green),
                    ChartColor.Solid(Color.Blue)
                )
            ),
            StackBarData(
                label = "Jul",
                values = listOf(40f, 50f, 60f),
                colors = listOf(
                    ChartColor.Solid(Color.Red),
                    ChartColor.Solid(Color.Green),
                    ChartColor.Solid(Color.Blue)
                )
            )
        )

        LineStackedBarChart(
            data = { data },
            target = 100f,
            modifier = Modifier.fillMaxWidth().height(300.dp).padding(24.dp)
        )

        StackedBarChart(
            data = { data },
            target = 100f,
            modifier = Modifier.fillMaxWidth().height(300.dp).padding(24.dp)
        )
    }
}

private fun generateSampleData(): List<BarData> {
    return listOf(
        BarData(1F, "Mon"),
        BarData(5F, "Mon"),
        BarData(2F, "Tue"),
        BarData(11F, "Tue"),
        BarData(3F, "Wed")
    )
}

private fun generateMockBarData(
    size: Int, useColor: Boolean = false, hasNegative: Boolean = true
): List<BarData> {
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val colors = listOf(
        Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan
    )

    val number = if (hasNegative) -10 else 0
    val data = List(size) {
        BarData(
            yValue = Random.nextFloat() * 20 + number, // Random value between -10 and 10
            xValue = days[it % days.size],
            barColor = if (useColor) colors[it % colors.size].asSolidChartColor() else Color.Unspecified.asSolidChartColor()
        )
    }.toMutableList()

    // Ensure one value is always 0
    if (data.isNotEmpty()) {
        val zeroIndex = Random.nextInt(size)
        data[zeroIndex] = data[zeroIndex].copy(yValue = 0f)
    }

    return data
}

private fun generateAllNegativeBarData(size: Int, useColor: Boolean = false): List<BarData> {
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val colors = listOf(
        Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan
    )

    return List(size) {
        BarData(
            yValue = -(Random.nextFloat() * 20), // Random negative value between -20 and 0
            xValue = days[it % days.size],
            barColor = if (useColor) colors[it % colors.size].asSolidChartColor() else Color.Unspecified.asSolidChartColor()
        )
    }
}

private fun generateMockStorageCategories(): List<StorageData> {
    return listOf(
        StorageData(
            name = "Document", value = 0.05f, color = Color(0xFFDC143C).asSolidChartColor()
        ), // Crimson Red
        StorageData(
            name = "Apps", value = 0.20f, color = Color(0xFFFFA500).asSolidChartColor()
        ), // Orange
        StorageData(
            name = "System", value = 0.10f, color = Color(0xFF2193b0).asSolidChartColor()
        ), // Yellow
        StorageData(
            name = "Music", value = 0.10f, color = Color(0xFF42275a).asSolidChartColor()
        ), // Green
    )
}

private fun generateMultiLineData(
    yValuesList: List<List<Float>>, xValues: List<String>, colorConfigs: List<LineChartColorConfig>
): List<MultiLineData> {
    require(yValuesList.all { it.size == xValues.size }) {
        "Each list of Y values must have the same size as the list of X values"
    }
    require(yValuesList.size == colorConfigs.size) {
        "The size of yValuesList and colorConfigs must be the same"
    }

    return yValuesList.mapIndexed { index, yValues ->
        MultiLineData(
            data = yValues.mapIndexed { i, yValue ->
                LineData(yValue, xValues[i])
            }, colorConfig = colorConfigs[index]
        )
    }
}

fun generateMockCircleData(): List<CircleData> {
    val colorLists = listOf(
        listOf(Color(0xFF00FF00), Color(0xFF0000FF)),
        listOf(Color(0xFFFFFF00), Color(0xFF00FFFF)),
        listOf(Color(0xFF000000), Color(0xFFFFA500))
    )
    return listOf(
        CircleData(
            value = 80F, // Random value between 0 and 100
            color = colorLists.first().asGradientChartColor(),
            label = "Label 1"
        ),
        CircleData(
            value = 90F, // Random value between 0 and 100
            color = colorLists[1].asGradientChartColor(),
            label = "Label 2"
        ),
        CircleData(
            value = 100F, // Random value between 0 and 100
            color = colorLists.first().asGradientChartColor(),
            label = "Label 3"
        ),

        )

}
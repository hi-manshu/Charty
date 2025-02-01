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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.HorizontalBarChart
import com.himanshoe.charty.bar.LineBarChart
import com.himanshoe.charty.bar.SignalProgressBarChart
import com.himanshoe.charty.bar.StorageBar
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.bar.model.StorageData
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.MultiLineChart
import com.himanshoe.charty.line.config.LineChartColorConfig
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.line.model.MultiLineData
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random


@Composable
@Preview
fun App() {
    LazyColumn {
        addLineChart()
        addMultiLineChart()
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


private fun LazyListScope.addMultiLineChart() {
    item {
        val xValues = listOf("Mon", "Tue", "Wed", "Thu", "Fri")
        val yValuesList = listOf(
            listOf(0F, 5F, 2F, 11F, 3F),
            listOf(10F, 5F, 12F, 11F, 30F)
        )
        val colorConfigs = listOf(
            LineChartColorConfig.default().copy(
                lineColor = ChartColor.Solid(Color.Red),
                lineFillColor = ChartColor.Gradient(
                    listOf(Color(0xFFCB356B), Color(0xFFBD3F32))
                )
            ),
            LineChartColorConfig.default().copy(
                lineColor = ChartColor.Solid(Color.Blue),
                lineFillColor = ChartColor.Gradient(
                    listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
                )
            )
        )
        val mockData = generateMultiLineData(yValuesList, xValues,colorConfigs)

        MultiLineChart(
            data = { mockData },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(300.dp),
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
            },
            modifier = Modifier.padding(10.dp).fillMaxWidth().height(300.dp)
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
                            .background(it.color), contentAlignment = Alignment.Center
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
            trackColors = listOf(Color.Gray, Color.Black),
            progressColors = listOf(
                Color(0xFFffafbd),
                Color(0xFFffc3a0),
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

fun generateSampleData(): List<BarData> {
    return listOf(
        BarData(1F, "Mon"),
        BarData(5F, "Mon"),
        BarData(2F, "Tue"),
        BarData(11F, "Tue"),
        BarData(3F, "Wed")
    )
}

fun generateMockBarData(
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
            barColor = if (useColor) colors[it % colors.size] else Color.Unspecified
        )
    }.toMutableList()

    // Ensure one value is always 0
    if (data.isNotEmpty()) {
        val zeroIndex = Random.nextInt(size)
        data[zeroIndex] = data[zeroIndex].copy(yValue = 0f)
    }

    return data
}

fun generateAllNegativeBarData(size: Int, useColor: Boolean = false): List<BarData> {
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val colors = listOf(
        Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan
    )

    return List(size) {
        BarData(
            yValue = -(Random.nextFloat() * 20), // Random negative value between -20 and 0
            xValue = days[it % days.size],
            barColor = if (useColor) colors[it % colors.size] else Color.Unspecified
        )
    }
}

fun generateMockStorageCategories(): List<StorageData> {
    return listOf(
        StorageData(name = "Document", value = 0.05f, color = Color(0xFFDC143C)), // Crimson Red
        StorageData(name = "Apps", value = 0.20f, color = Color(0xFFFFA500)), // Orange
        StorageData(name = "System", value = 0.10f, color = Color(0xFF2193b0)), // Yellow
        StorageData(name = "Music", value = 0.10f, color = Color(0xFF42275a)), // Green
    )
}
fun generateMultiLineData(
    yValuesList: List<List<Float>>,
    xValues: List<String>,
    colorConfigs: List<LineChartColorConfig>
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
            },
            colorConfig = colorConfigs[index]
        )
    }
}
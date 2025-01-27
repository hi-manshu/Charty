package com.himanshoe.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.config.BarChartColorConfig
import com.himanshoe.charty.bar.LineBarChart
import com.himanshoe.charty.signalProgressBar.SignalProgressBarChart
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.stockageBar.StorageBar
import com.himanshoe.charty.stockageBar.model.StorageData
import com.himanshoe.myapplication.ui.theme.ChartyKMPTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChartyKMPTheme {
                LazyColumn {
                    addStorageBarChart()
                    addSignalBarChart()
                    addLineBarChart(3F, generateSampleData())
                    addLineBarChart(null, generateMockData(7))
                    addBarChart(2F, generateMockData(11))
                    addBarChart(null, generateMockData(7, false))
                    addBarChart(null, generateMockData(7, false, false))
                }
            }
        }
    }

    private fun LazyListScope.addStorageBarChart() {
        item {
            StorageBar(
                data = generateMockStorageCategories(),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(12.dp)
                    .fillMaxWidth()
                    .height(30.dp)
            )
        }
    }

    private fun LazyListScope.addSignalBarChart() {
        item {
            SignalProgressBarChart(
                progress = { 79F }, modifier = Modifier
                    .padding(all = 12.dp)
                    .fillMaxWidth(0.15F)
                    .height(300.dp),
                trackColors = listOf(Color.Gray, Color.Black),
                progressColors = listOf(
                    Color(0xFFffafbd),
                    Color(0xFFffc3a0),
                ),
                gapRatio = 0.1F
            )
        }
    }

    private fun LazyListScope.addLineBarChart(target: Float?, data: List<BarData>) {
        item {
            LineBarChart(target = target,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(300.dp),
                barChartColorConfig = BarChartColorConfig(
                    defaultGradientBarColors = listOf(Color.Blue, Color.Green),
                    negativeGradientBarColors = listOf(Color.Gray, Color.Black)
                ),
                data = data,
                onBarClick = { _, barData: BarData -> Log.d("LineBarChart", barData.toString()) })
        }
    }

    private fun LazyListScope.addBarChart(target: Float?, data: List<BarData>) {
        item {
            BarChart(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(300.dp),
                target = target,
                barChartColorConfig = BarChartColorConfig(
                    defaultGradientBarColors = listOf(Color.Blue, Color.Green),
                    negativeGradientBarColors = listOf(Color.Gray, Color.Black)
                ),
                data = data,
                onBarClick = { _, barData -> Log.d("BarChart", barData.toString()) })
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

    private fun generateMockData(
        size: Int, useColor: Boolean = true, hasNegative: Boolean = true
    ): List<BarData> {
        val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        val colors = listOf(
            Color.Red,
            Color.Green,
            Color.Blue,
            Color.Yellow,
            Color.DarkGray,
            Color.Magenta,
            Color.Cyan
        )

        val number = if (hasNegative) -10 else 0
        return List(size) {
            BarData(
                yValue = Random.nextFloat() * 20 + number, // Random value between -10 and 10
                xValue = days[it % days.size],
                barColor = if (useColor) colors[it % colors.size] else Color.Unspecified
            )
        }
    }

    private fun generateMockStorageCategories(): List<StorageData> {
        return listOf(
            StorageData(name = "System", value = 0.05f, color = Color.Gray),
            StorageData(name = "Apps", value = 0.20f, color = Color.Red),
            StorageData(name = "Garima", value = 0.10f, color = Color.Magenta),
            StorageData(name = "Himanshu", value = 0.10f, color = Color.Blue),
        )
    }
}

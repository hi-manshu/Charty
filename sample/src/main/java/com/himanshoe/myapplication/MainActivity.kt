package com.himanshoe.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.BarChartColorConfig
import com.himanshoe.charty.bar.LineBarChart
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.myapplication.ui.theme.ChartyKMPTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChartyKMPTheme {
                LazyColumn {
                    addLineBarChart(3F, generateSampleData())
                    addLineBarChart(null, generateMockData(7))
                    addBarChart(2F, generateMockData(11))
                    addBarChart(null, generateMockData(7, false))
                    addBarChart(null, generateMockData(7, false, false))
                }
            }
        }
    }

    private fun LazyListScope.addLineBarChart(target: Float?, data: List<BarData>) {
        item {
            LineBarChart(
                target = target,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(300.dp),
                barChartColorConfig = BarChartColorConfig(
                    defaultGradientBarColors = listOf(Color.Blue, Color.Green),
                    negativeGradientBarColors = listOf(Color.Gray, Color.Black)
                ),
                data = data,
                onBarClick = { _, barData: BarData -> Log.d("LineBarChart", barData.toString()) }
            )
        }
    }

    private fun LazyListScope.addBarChart(target: Float?, data: List<BarData>) {
        item {
            BarChart(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(300.dp),
                target = target,
                barChartColorConfig = BarChartColorConfig(
                    defaultGradientBarColors = listOf(Color.Blue, Color.Green),
                    negativeGradientBarColors = listOf(Color.Gray, Color.Black)
                ),
                data = data,
                onBarClick = { _, barData -> Log.d("BarChart", barData.toString()) }
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

    private fun generateMockData(
        size: Int,
        useColor: Boolean = true,
        hasNegative: Boolean = true
    ): List<BarData> {
        val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        val colors = listOf(
            Color.Red, Color.Green, Color.Blue, Color.Yellow,
            Color.DarkGray, Color.Magenta, Color.Cyan
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
}
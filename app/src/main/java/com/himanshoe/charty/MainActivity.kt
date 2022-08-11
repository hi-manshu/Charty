package com.himanshoe.charty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.circle.CircleChart
import com.himanshoe.charty.circle.model.CircleData
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.pie.PieChart
import com.himanshoe.charty.point.PointChart
import com.himanshoe.charty.point.model.PointData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumn(Modifier.fillMaxSize()) {
                item {
                    PieChart(
                        modifier = Modifier
                            .scale(1f)
                            .size(400.dp)
                            .padding(20.dp),
                        data = listOf(
                            20F,
                            50F,
                            100F,
                            70F
                        ),
                        colors = listOf(
                            Color(0xFFbf95d4),
                            Color(0xFFf4ac1a),
                            Color(0xFF8b0a50),
                            Color(0xFF3a0a55),
                        ),
                        isDonut = false,
                        percentColor = Color.Black
                    )
                }
                item {
                    CircleChart(
                        modifier = Modifier
                            .scale(1f)
                            .size(400.dp)
                            .padding(20.dp),
                        circleData = listOf(
                            CircleData(100F, 100F, Color.Green),
                            CircleData(1F, 30F, Color.Green),
                            CircleData(20F, 35F, Color.Green),
                            CircleData(10F, 350F, Color.Green),
                            CircleData(10F, 95F, Color.Green),
                            CircleData(10F, 75F, Color.Green),
                            CircleData(10F, 335F, Color.Green),
                            CircleData(10F, 235F, Color.Green),
                            CircleData(10F, 135F, Color.Green),
                            CircleData(10F, 315F, Color.Green),
                            CircleData(20F, 5F, Color.Green),
                            CircleData(30F, 315F)
                        ),
                        color = Color.Yellow
                    )
                }

                item {
                    BarChart(
                        modifier = Modifier
                            .padding(top = 100.dp)
                            .size(width = 500.dp, height = 300.dp)
                            .padding(20.dp),
                        onBarClick = {
                        },
                        colors = listOf(Color.Green, Color.Black),
                        barData = listOf(
                            BarData(10F, 35F),
                            BarData(20F, 25F),
                            BarData(10F, 50F),
                            BarData(100F, 10F),
                            BarData(10F, 15F),
                            BarData(50F, 100F),
                            BarData(20F, 25F),
                        )
                    )
                }
                item {
                    LineChart(
                        modifier = Modifier
                            .padding(top = 100.dp)
                            .size(width = 500.dp, height = 300.dp)
                            .padding(20.dp),
                        colors = listOf(Color.Green, Color.Black),
                        lineData = listOf(
                            LineData(10F, 35F),
                            LineData(20F, 25F),
                            LineData(10F, 50F),
                            LineData(100F, 10F),
                            LineData(10F, 15F),
                            LineData(50F, 100F),
                            LineData(20F, 25F),
                        )
                    )
                }
                item {
                    PointChart(
                        modifier = Modifier
                            .padding(top = 100.dp)
                            .size(width = 500.dp, height = 300.dp)
                            .padding(20.dp),
                        colors = listOf(Color.Green, Color.Black),
                        pointData = listOf(
                            PointData(10F, 35F),
                            PointData(20F, 25F),
                            PointData(10F, 50F),
                            PointData(100F, 10F),
                            PointData(10F, 15F),
                            PointData(50F, 100F),
                            PointData(20F, 25F),
                        )
                    )
                }
            }
        }
    }
}


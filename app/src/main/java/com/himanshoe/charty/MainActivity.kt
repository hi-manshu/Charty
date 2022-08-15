package com.himanshoe.charty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            LazyColumn(Modifier.fillMaxSize()) {
//                item {
//                    GroupedHorizontalBarChart(
//                        modifier = Modifier
//                            .size(width = 500.dp, height = 300.dp)
//                            .padding(20.dp),
//                        groupedBarData = listOf(
//                            GroupedHorizontalBarData(
//                                listOf(
//                                    HorizontalBarData(10F, 35F),
//                                    HorizontalBarData(20F, 25F),
//                                    HorizontalBarData(10F, 50F),
//                                ),
//                                colors = listOf(Color.Black, Color.Green, Color.Yellow)
//                            ),
//                            GroupedHorizontalBarData(
//                                listOf(
//                                    HorizontalBarData(10F, 35F),
//                                    HorizontalBarData(20F, 25F),
//                                    HorizontalBarData(10F, 50F),
//                                ),
//                                colors = listOf(Color.Black, Color.Green, Color.Yellow)
//                            ),
//                            GroupedHorizontalBarData(
//                                listOf(
//                                    HorizontalBarData(10F, 35F),
//                                    HorizontalBarData(20F, 25F),
//                                    HorizontalBarData(10F, 50F),
//                                ),
//                                colors = listOf(Color.Black, Color.Green, Color.Yellow)
//                            ),
//                        ),
//                        onBarClick = {
//                        }
//                    )
//                }
//                item {
//                    HorizontalBarChart(
//                        modifier = Modifier
//                            .size(width = 500.dp, height = 300.dp)
//                            .padding(20.dp),
//                        colors = listOf(
//                            Color.Green,
//                            Color.Black,
//                        ),
//                        horizontalBarData = listOf(
//                            HorizontalBarData(10F, 10F),
//                            HorizontalBarData(20F, "25F"),
//                            HorizontalBarData(75F, "100F"),
//                            HorizontalBarData(40F, "Himan"),
//                            HorizontalBarData(10F, "100F"),
//                            HorizontalBarData(20F, "25F"),
//                        ),
//                        onBarClick = {
//                        }
//                    )
//                }
//                item {
//                    CurveLineChart(
//                        modifier = Modifier
//                            .padding(top = 100.dp)
//                            .size(width = 500.dp, height = 300.dp)
//                            .padding(20.dp),
//                        colors = listOf(
//                            Color.Green,
//                            Color.Black,
//                        ),
//                        lineData = listOf(
//                            LineData(10F, 35F),
//                            LineData(20F, 25F),
//                            LineData(50F, 100F),
//                            LineData(20F, 25F),
//
//                        )
//                    )
//                }
//                item {
//
//                }
//
//                item {
//                    GroupedBarChart(
//                        modifier = Modifier
//                            .size(width = 500.dp, height = 300.dp)
//                            .padding(20.dp),
//                        groupedBarData = listOf(
//                            GroupedBarData(
//                                listOf(
//                                    BarData(10F, 35F),
//                                    BarData(20F, 25F),
//                                    BarData(10F, 50F),
//                                ),
//                                colors = listOf(Color.Black, Color.Green, Color.Yellow)
//                            ),
//                            GroupedBarData(
//                                listOf(
//                                    BarData(10F, 35F),
//                                    BarData(20F, 25F),
//                                    BarData(10F, 50F),
//                                ),
//                                colors = listOf(Color.Black, Color.Green, Color.Yellow)
//                            ),
//                            GroupedBarData(
//                                listOf(
//                                    BarData(10F, 35F),
//                                    BarData(20F, 25F),
//                                    BarData(10F, 50F),
//                                ),
//                                colors = listOf(Color.Black, Color.Green, Color.Yellow)
//                            ),
//                        ),
//                    )
//                }
//                item {
//                    PieChart(
//                        modifier = Modifier
//                            .scale(1f)
//                            .size(400.dp)
//                            .padding(20.dp),
//                        data = listOf(20F, 50F, 100F, 70F, 20F, 50F, 100F, 70F),
//                        isDonut = true,
//                        valueTextColor = Color.Black,
//                        onSectionClicked = { percent, value ->
//                        }
//                    )
//                }
//                item {
//                    CircleChart(
//                        modifier = Modifier
//                            .scale(1f)
//                            .size(400.dp)
//                            .padding(20.dp),
//                        circleData = listOf(
//                            CircleData(10F, 235F, Color.Green),
//                            CircleData(10F, 135F, Color.Green),
//                            CircleData(10F, 315F, Color.Green),
//                            CircleData(20F, 50F, Color.Green),
//                            CircleData(30F, 315F)
//                        ),
//                        color = Color.Yellow
//                    )
//                }
//
//                item {
//                    LineChart(
//                        modifier = Modifier
//                            .padding(top = 100.dp)
//                            .size(width = 500.dp, height = 300.dp)
//                            .padding(20.dp),
//                        colors = listOf(Color.Green, Color.Black),
//                        lineData = listOf(
//                            LineData(10F, 35F),
//                            LineData(20F, 25F),
//                            LineData(10F, 50F),
//                            LineData(100F, 10F),
//                            LineData(10F, 15F),
//                            LineData(50F, 100F),
//                            LineData(20F, 25F),
//                        )
//                    )
//                }
//
//                item {
//                    PointChart(
//                        modifier = Modifier
//                            .padding(top = 100.dp)
//                            .size(width = 500.dp, height = 300.dp)
//                            .padding(20.dp),
//                        colors = listOf(Color.Green, Color.Black),
//                        pointData = listOf(
//                            PointData(10F, 35F),
//                            PointData(20F, 25F),
//                            PointData(10F, 50F),
//                            PointData(10F, 50F),
//                            PointData(100F, 10F),
//                            PointData(10F, 15F),
//                            PointData(50F, 100F),
//                            PointData(20F, 25F),
//                        )
//                    )
//                }
//            }
            val navigator = rememberNavController()
            RegisterNavigation(navigator = navigator,
                onBarChartClicked = {
                    navigator.navigate("barchart")
                }, onHorizontalBarChartClicked = {
                    navigator.navigate("horizontalBarChartDemo")
                }, onCircleChartClicked = {
                    navigator.navigate("circlechart")
                }, onLineChartClicked = {
                    navigator.navigate("linechart")
                }, onCurveChartClicked = {
                    navigator.navigate("curvelinechart")
                }, onPointChartClicked = {
                    navigator.navigate("pointchart")
                }, onPieChartClicked = {
                    navigator.navigate("piechart")
                }, onGroupHorizontalClicked = {
                    navigator.navigate("grouphorizontalbar")
                }, onGroupBarClicked = {
                    navigator.navigate("groupbar")
                }
            )
        }
    }
}

@Composable
fun MainApp(
    onBarChartClicked: () -> Unit,
    onHorizontalBarChartClicked: () -> Unit,
    onCircleChartClicked: () -> Unit,
    onLineChartClicked: () -> Unit,
    onCurveChartClicked: () -> Unit,
    onPointChartClicked: () -> Unit,
    onPieChartClicked: () -> Unit,
    onGroupHorizontalClicked: () -> Unit,
    onGroupBarClicked: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = { onBarChartClicked() }) {
                Text(text = "Bar Chart")
            }
        }
        item {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = { onCircleChartClicked() }) {
                Text(text = "Circle Chart")
            }
        }
        item {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = { onCurveChartClicked() }) {
                Text(text = "Curve Line Chart")
            }
        }
        item {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = { onLineChartClicked() }) {
                Text(text = "Line Chart")
            }
        }
        item {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = { onGroupBarClicked() }) {
                Text(text = "Grouped bar Chart")
            }
        }
        item {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = { onGroupHorizontalClicked() }) {
                Text(text = "Grouped HorizontalBar Chart")
            }
        }
        item {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = { onHorizontalBarChartClicked() }) {
                Text(text = "HorizontalBar Chart")
            }
        }
        item {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = { onPieChartClicked() }) {
                Text(text = "Pie Chart")
            }
        }
        item {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = { onPointChartClicked() }) {
                Text(text = "Point Chart")
            }
        }
    }
}

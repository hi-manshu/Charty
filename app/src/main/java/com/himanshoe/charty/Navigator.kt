package com.himanshoe.charty

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.himanshoe.charty.ui.*


@Composable
fun RegisterNavigation(
    navigator: NavHostController,
    onBarChartClicked: () -> Unit,
    onHorizontalBarChartClicked: () -> Unit,
    onCircleChartClicked: () -> Unit,
    onLineChartClicked: () -> Unit,
    onCurveChartClicked: () -> Unit,
    onPointChartClicked: () -> Unit,
) {
    NavHost(navController = navigator, startDestination = "main") {
        composable("barchart") { BarChartDemo() }
        composable("main") {
            MainApp(
                onBarChartClicked,
                onHorizontalBarChartClicked,
                onCircleChartClicked,
                onLineChartClicked,
                onCurveChartClicked,
                onPointChartClicked
            )
        }
        composable("horizontalBarChartDemo") { HorizontalBarChartDemo() }
        composable("circlechart") { CircleChartDemo() }
        composable("linechart") { LineChartDemo() }
        composable("curvelinechart") { CurveLineChartDemo() }
        composable("pointchart") { PointChartDemo() }
    }
}

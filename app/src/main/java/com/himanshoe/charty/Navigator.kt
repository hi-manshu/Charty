package com.himanshoe.charty

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onPieChartClicked: () -> Unit,
    onGroupHorizontalClicked: () -> Unit,
    onGroupBarClicked: () -> Unit,
    onCombinedBarChartClicked: () -> Unit,
    onCandleChartClicked: () -> Unit,
    onStackedBarClicked: () -> Unit,
    onBubbleChartClicked: () -> Unit,
    onLinearRegressionChartClicked: () -> Unit
) {
    NavHost(
        modifier = Modifier.background(Color.Black),
        navController = navigator,
        startDestination = "main"
    ) {
        composable("barchart") { BarChartDemo() }
        composable("main") {
            MainApp(
                onBarChartClicked = onBarChartClicked,
                onBubbleChartClicked = onBubbleChartClicked,
                onGroupBarClicked = onGroupBarClicked,
                onHorizontalBarChartClicked = onHorizontalBarChartClicked,
                onCircleChartClicked = onCircleChartClicked,
                onLineChartClicked = onLineChartClicked,
                onCurveChartClicked = onCurveChartClicked,
                onPointChartClicked = onPointChartClicked,
                onPieChartClicked = onPieChartClicked,
                onGroupHorizontalClicked = onGroupHorizontalClicked,
                onCombinedBarChartClicked = onCombinedBarChartClicked,
                onCandleChartClicked = onCandleChartClicked,
                onStackedBarClicked = onStackedBarClicked,
                onLinearRegressionClicked = onLinearRegressionChartClicked
            )
        }
        composable("horizontalBarChartDemo") { HorizontalBarChartDemo() }
        composable("circlechart") { CircleChartDemo() }
        composable("linechart") { LineChartDemo() }
        composable("curvelinechart") { CurveLineChartDemo() }
        composable("stackedBar") { StackedBarChartDemo() }
        composable("pointchart") { PointChartDemo() }
        composable("piechart") { PieChartDemo() }
        composable("grouphorizontalbar") { GroupedHorizontalBarChartDemo() }
        composable("groupbar") { GroupBarChartDemo() }
        composable("combinedBar") { CombinedBarChartDemo() }
        composable("candleChart") { CandleStickChartDemo() }
        composable("bubbleChart") { BubbleChartDemo() }
        composable("linearRegressionChart") { LinearRegressionChartDemo() }
    }
}

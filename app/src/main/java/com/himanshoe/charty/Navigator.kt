package com.himanshoe.charty

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.himanshoe.charty.ui.BarChartDemo
import com.himanshoe.charty.ui.BubbleChartDemo
import com.himanshoe.charty.ui.CandleStickChartDemo
import com.himanshoe.charty.ui.CircleChartDemo
import com.himanshoe.charty.ui.CombinedBarChartDemo
import com.himanshoe.charty.ui.CurveLineChartDemo
import com.himanshoe.charty.ui.GroupBarChartDemo
import com.himanshoe.charty.ui.GroupedHorizontalBarChartDemo
import com.himanshoe.charty.ui.HorizontalBarChartDemo
import com.himanshoe.charty.ui.LineChartDemo
import com.himanshoe.charty.ui.PieChartDemo
import com.himanshoe.charty.ui.PointChartDemo
import com.himanshoe.charty.ui.StackedBarChartDemo

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
    onBubbleChartClicked: () -> Unit
) {
    NavHost(navController = navigator, startDestination = "main") {
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
                onStackedBarClicked = onStackedBarClicked
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
    }
}

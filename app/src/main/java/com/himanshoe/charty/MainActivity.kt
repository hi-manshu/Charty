package com.himanshoe.charty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
            val navigator = rememberNavController()
            RegisterNavigation(
                navigator = navigator,
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
            }, onCombinedBarChartClicked = {
                navigator.navigate("combinedBar")
            }, onCandleChartClicked = {
                navigator.navigate("candleChart")
            }, onStackedBarClicked = {
                navigator.navigate("stackedBar")
            }, onBubbleChartClicked = {
                navigator.navigate("bubbleChart")
            }
            )
        }
    }
}

@Composable
fun MainApp(
    onBarChartClicked: () -> Unit,
    onBubbleChartClicked: () -> Unit,
    onCombinedBarChartClicked: () -> Unit,
    onHorizontalBarChartClicked: () -> Unit,
    onCircleChartClicked: () -> Unit,
    onLineChartClicked: () -> Unit,
    onCurveChartClicked: () -> Unit,
    onPointChartClicked: () -> Unit,
    onPieChartClicked: () -> Unit,
    onGroupHorizontalClicked: () -> Unit,
    onCandleChartClicked: () -> Unit,
    onGroupBarClicked: () -> Unit,
    onStackedBarClicked: () -> Unit,
) {

    val list: List<Pair<String, () -> Unit>> =
        listOf(
            "Bar Chart" to onBarChartClicked,
            "Bubble Chart" to onBubbleChartClicked,
            "Stack Bar Chart" to onStackedBarClicked,
            "Candle Chart" to onCandleChartClicked,
            "Combined Bar Chart" to onCombinedBarChartClicked,
            "Circle Chart" to onCircleChartClicked,
            "Curve Line Chart" to onCurveChartClicked,
            "Line Chart" to onLineChartClicked,
            "Grouped Bar Chart" to onGroupBarClicked,
            "Grouped Horizontal Chart" to onGroupHorizontalClicked,
            "Horizontal Bar Chart" to onHorizontalBarChartClicked,
            "Pie Chart" to onPieChartClicked,
            "Point Chart" to onPointChartClicked
        )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        items(list) { item ->
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                onClick = item.second
            ) {
                Text(item.first)
            }
        }
    }
}

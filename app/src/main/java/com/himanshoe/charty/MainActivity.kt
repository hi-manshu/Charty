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
            }
            )
        }
    }
}

@Composable
fun MainApp(
    onBarChartClicked: () -> Unit,
    onCombinedBarChartClicked: () -> Unit,
    onHorizontalBarChartClicked: () -> Unit,
    onCircleChartClicked: () -> Unit,
    onLineChartClicked: () -> Unit,
    onCurveChartClicked: () -> Unit,
    onPointChartClicked: () -> Unit,
    onPieChartClicked: () -> Unit,
    onGroupHorizontalClicked: () -> Unit,
    onGroupBarClicked: () -> Unit,
) {

    val list: List<Pair<String, () -> Unit>> =
        listOf(
            "Bar Chart" to onBarChartClicked,
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

//        item {
//            ExtendedFloatingActionButton(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                onClick = { onBarChartClicked() }) {
//                Text(text = "Bar Chart")
//            }
//        }
//        item {
//            ExtendedFloatingActionButton(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                onClick = { onCircleChartClicked() }) {
//                Text(text = "Circle Chart")
//            }
//        }
//        item {
//            ExtendedFloatingActionButton(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                onClick = { onCurveChartClicked() }) {
//                Text(text = "Curve Line Chart")
//            }
//        }
//        item {
//            ExtendedFloatingActionButton(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                onClick = { onLineChartClicked() }) {
//                Text(text = "Line Chart")
//            }
//        }
//        item {
//            ExtendedFloatingActionButton(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                onClick = { onGroupBarClicked() }) {
//                Text(text = "Grouped bar Chart")
//            }
//        }
//        item {
//            ExtendedFloatingActionButton(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                onClick = { onGroupHorizontalClicked() }) {
//                Text(text = "Grouped HorizontalBar Chart")
//            }
//        }
//        item {
//            ExtendedFloatingActionButton(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                onClick = { onHorizontalBarChartClicked() }) {
//                Text(text = "HorizontalBar Chart")
//            }
//        }
//        item {
//            ExtendedFloatingActionButton(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                onClick = { onPieChartClicked() }) {
//                Text(text = "Pie Chart")
//            }
//        }
//        item {
//            ExtendedFloatingActionButton(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                onClick = { onPointChartClicked() }) {
//                Text(text = "Point Chart")
//            }
//        }
    }
}

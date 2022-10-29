package com.himanshoe.charty.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.config.LineConfig
import com.himanshoe.charty.line.model.LineData
import kotlin.random.Random

@Composable
fun LineChartDemo() {
    val dynamicData = remember {
        mutableStateListOf(
            LineData(10F, 35F),
            LineData(10F, 45F),
        )
    }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        item {


            Column {
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        val lastItem = dynamicData.last()
                        dynamicData.add(
                            LineData((lastItem.xValue as Float) + 10f, lastItem.yValue + Random.nextFloat() * 10f)
                        )
                    }
                ) {
                    Text(text = "Add random item")
                }
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    colors = colors,
                    lineData = dynamicData
                )
            }
        }
        item {
            Text(
                text = "Line Chart with dynamic data",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }

        item {
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                colors = colors,
                lineData = listOf(
                    LineData(10F, 35F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(20F, 25F),
                    LineData(10F, 50F),
                    LineData(80F, 10F),
                    LineData(10F, 15F),
                    LineData(50F, 100F),
                    LineData(20F, 25F),
                )
            )
        }
        item {
            Text(
                text = "Gradient Line Chart",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }
        item {
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                color = colors.first(),
                lineData = listOf(
                    LineData(10F, 35F),
                    LineData(20F, 25F),
                    LineData(10F, 50F),
                    LineData(80F, 10F),
                    LineData(10F, 15F),
                    LineData(50F, 100F),
                    LineData(20F, 25F),
                )
            )
        }
        item {
            Text(
                text = "Solid Line Chart",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }
        item {
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                color = colors.first(),
                lineConfig = LineConfig(hasDotMarker = false),
                lineData = listOf(
                    LineData(10F, 35F),
                    LineData(20F, 25F),
                    LineData(10F, 50F),
                    LineData(80F, 10F),
                    LineData(10F, 15F),
                    LineData(50F, 100F),
                    LineData(20F, 25F),
                )
            )
        }
        item {
            Text(
                text = "Line Chart without marker",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }
        item {
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                color = colors.first(),
                lineConfig = LineConfig(hasSmoothCurve = true),
                lineData = listOf(
                    LineData(10F, 35F),
                    LineData(20F, 25F),
                    LineData(10F, 50F),
                    LineData(80F, 10F),
                    LineData(10F, 15F),
                    LineData(50F, 100F),
                    LineData(20F, 25F),
                )
            )
        }
        item {
            Text(
                text = "Line Chart with smooth curve",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

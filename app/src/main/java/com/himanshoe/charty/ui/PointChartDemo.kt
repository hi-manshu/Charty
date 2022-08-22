package com.himanshoe.charty.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himanshoe.charty.point.PointChart
import com.himanshoe.charty.point.cofig.PointConfig
import com.himanshoe.charty.point.cofig.PointType
import com.himanshoe.charty.point.model.PointData

@Composable
fun PointChartDemo() {
    LazyColumn(
        Modifier
            .fillMaxSize()
    ) {
        item {
            PointChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 300.dp)
                    .padding(20.dp),
                colors = colors,
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
        item {
            Text(
                text = "Gradient Point Chart",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        item {
            PointChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 300.dp)
                    .padding(20.dp),
                color = colors.first(),
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
        item {
            Text(
                text = "Solid Point Chart",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        item {
            PointChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 300.dp)
                    .padding(20.dp),
                color = colors.first(),
                pointConfig = PointConfig(pointType = PointType.Fill),
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
        item {
            Text(
                text = "Solid Filled Chart (You can also make a gradient filled like above)",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

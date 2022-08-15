package com.himanshoe.charty.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.model.BarData

@Composable
fun BarChartDemo() {
    LazyColumn(
        Modifier
            .fillMaxSize()
    ) {
        item {
            BarChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(32.dp),
                onBarClick = {},
                colors = listOf(Color(0xFFFDC830), Color(0xFFF37335)),
                barData = listOf(
                    BarData(10F, 35F),
                    BarData(20F, 25F),
                    BarData(10F, 50F),
                    BarData(60F, 10F),
                    BarData(10F, 15F),
                    BarData(50F, 100F),
                    BarData(20F, 25F),
                )
            )
        }
        item {
            Text(
                text = "Gradient Bar Chart",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        item {
            BarChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(32.dp),
                onBarClick = {},
                color = Color(0xFFD2827A),
                barData = listOf(
                    BarData(10F, 35F),
                    BarData(20F, 25F),
                    BarData(10F, 50F),
                    BarData(60F, 10F),
                    BarData(10F, 15F),
                    BarData(50F, 100F),
                    BarData(20F, 25F),
                )
            )
        }
        item {
            Text(
                fontSize = 16.sp,
                text = "Solid Bar Chart",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
        }

    }
}

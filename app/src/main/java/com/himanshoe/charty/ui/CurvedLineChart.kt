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
import com.himanshoe.charty.line.CurveLineChart
import com.himanshoe.charty.line.config.CurveLineConfig
import com.himanshoe.charty.line.model.LineData

@Composable
fun CurveLineChartDemo() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        item {
            CurveLineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 300.dp),
                chartColors = colors,
                lineColors = pcolors,
                lineData = listOf(
                    LineData(10F, 35F),
                    LineData(20F, 25F),
                    LineData(50F, 100F),
                    LineData(20F, 25F),
                )
            )
        }
        item {
            Text(
                text = "Curved Line Graph with Dot Marker",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }

        item {
            CurveLineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 300.dp),
                chartColors = colors,
                lineColors = pcolors,
                lineData = listOf(
                    LineData(10F, 35F),
                    LineData(20F, 25F),
                    LineData(50F, 100F),
                    LineData(20F, 25F),
                ),
                curveLineConfig = CurveLineConfig(false)
            )
        }
        item {
            Text(
                text = "Curved Line Graph without Dot Marker",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

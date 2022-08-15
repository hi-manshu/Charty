package com.himanshoe.charty.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himanshoe.charty.horizontalbar.HorizontalBarChart
import com.himanshoe.charty.horizontalbar.config.HorizontalBarConfig
import com.himanshoe.charty.horizontalbar.config.StartDirection
import com.himanshoe.charty.horizontalbar.model.HorizontalBarData

private val horizontalBarData = listOf(
    HorizontalBarData(10F, "A"),
    HorizontalBarData(20F, "B"),
    HorizontalBarData(75F, "C"),
    HorizontalBarData(40F, "D"),
    HorizontalBarData(10F, "E"),
    HorizontalBarData(20F, "F"),
)

@Composable
fun HorizontalBarChartDemo() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            HorizontalBarChart(
                modifier = Modifier
                    .size(width = 500.dp, height = 300.dp)
                    .padding(20.dp),
                colors = listOf(Color(0xFFFDC830), Color(0xFFF37335)),
                horizontalBarData = horizontalBarData,
                onBarClick = {
                }
            )
        }
        item {
            Text(
                text = "Start destination = Right",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }
        item {
            HorizontalBarChart(
                modifier = Modifier
                    .size(width = 500.dp, height = 300.dp)
                    .padding(20.dp),
                colors = listOf(Color(0xFFFDC830), Color(0xFFF37335)),
                horizontalBarData = horizontalBarData,
                horizontalBarConfig = HorizontalBarConfig(true, StartDirection.Left),
                onBarClick = {}
            )
        }
        item {
            Text(
                text = "Start destination = Left",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

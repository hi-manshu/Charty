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
import com.himanshoe.charty.horizontalbar.GroupedHorizontalBarChart
import com.himanshoe.charty.horizontalbar.config.HorizontalBarConfig
import com.himanshoe.charty.horizontalbar.config.StartDirection
import com.himanshoe.charty.horizontalbar.model.GroupedHorizontalBarData
import com.himanshoe.charty.horizontalbar.model.HorizontalBarData

val pcolors = listOf(Color(0xFFFDC830), Color(0xFFF37335), Color.LightGray)

@Composable
fun GroupedHorizontalBarChartDemo() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            GroupedHorizontalBarChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 300.dp),
                groupedBarData = listOf(
                    GroupedHorizontalBarData(
                        listOf(
                            HorizontalBarData(15F, 30F),
                            HorizontalBarData(25F, 40F),
                            HorizontalBarData(13F, 50F),
                        ),
                        colors = pcolors
                    ),
                    GroupedHorizontalBarData(
                        listOf(
                            HorizontalBarData(15F, 50F),
                            HorizontalBarData(25F, 70F),
                            HorizontalBarData(13F, 80F),
                        ),
                        colors = pcolors
                    ),
                    GroupedHorizontalBarData(
                        listOf(
                            HorizontalBarData(15F, 90F),
                            HorizontalBarData(25F, 100F),
                            HorizontalBarData(13F, 50F),
                        ),
                        colors = pcolors
                    ),
                ),
                onBarClick = {}
            )
        }
        item {
            Text(
                text = "Group Chart with Start Direction Right",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }
        item {
            GroupedHorizontalBarChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 300.dp),
                horizontalBarConfig = HorizontalBarConfig(startDirection = StartDirection.Left),
                groupedBarData = listOf(
                    GroupedHorizontalBarData(
                        listOf(
                            HorizontalBarData(15F, 30F),
                            HorizontalBarData(25F, 40F),
                            HorizontalBarData(13F, 50F),
                        ),
                        colors = pcolors
                    ),
                    GroupedHorizontalBarData(
                        listOf(
                            HorizontalBarData(15F, 50F),
                            HorizontalBarData(25F, 70F),
                            HorizontalBarData(13F, 80F),
                        ),
                        colors = pcolors
                    ),
                    GroupedHorizontalBarData(
                        listOf(
                            HorizontalBarData(15F, 90F),
                            HorizontalBarData(25F, 100F),
                            HorizontalBarData(13F, 50F),
                        ),
                        colors = pcolors
                    ),
                ),
                onBarClick = {}
            )
        }
        item {
            Text(
                text = "Group Chart with Start Direction Left",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

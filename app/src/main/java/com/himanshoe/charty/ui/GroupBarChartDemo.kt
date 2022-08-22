package com.himanshoe.charty.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.bar.GroupedBarChart
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.bar.model.GroupedBarData

@Composable
fun GroupBarChartDemo() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            GroupedBarChart(
                modifier = Modifier
                    .size(width = 500.dp, height = 300.dp)
                    .padding(20.dp),
                groupedBarData = listOf(
                    GroupedBarData(
                        listOf(
                            BarData(10F, 35F),
                            BarData(20F, 25F),
                            BarData(10F, 50F),
                        ),
                        colors = pcolors
                    ),
                    GroupedBarData(
                        listOf(
                            BarData(10F, 35F),
                            BarData(20F, 25F),
                            BarData(10F, 50F),
                        ),
                        colors = pcolors
                    ),
                    GroupedBarData(
                        listOf(
                            BarData(10F, 35F),
                            BarData(20F, 25F),
                            BarData(10F, 50F),
                        ),
                        colors = pcolors
                    ),
                ),
            )
        }
    }
}

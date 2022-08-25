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
import com.himanshoe.charty.combined.CombinedBarChart
import com.himanshoe.charty.combined.model.CombinedBarData

@Composable
fun CombinedBarChartDemo() {
    LazyColumn(
        Modifier
            .fillMaxSize()
    ) {
        item {
            CombinedBarChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(32.dp),
                onClick = {},
                barColors = colors,
                combinedBarData = listOf(
                    CombinedBarData(10F, 80F, 5F),
                    CombinedBarData(10F, 45F, 40F),
                    CombinedBarData(10F, 30F, 15F),
                    CombinedBarData(10F, 25F, 20F),
                    CombinedBarData(10F, 30F, 25F),
                    CombinedBarData(10F, 25F, 30F),
                    CombinedBarData(10F, 5F, 35F),
                ),
                lineColors = listOf(Color.White, Color.Green, Color.Cyan)
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
    }
}

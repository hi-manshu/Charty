package com.himanshoe.charty.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himanshoe.charty.pie.PieChart

@Composable
fun PieChartDemo() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            PieChart(
                modifier = Modifier
                    .scale(1f)
                    .size(400.dp)
                    .padding(20.dp),
                data = listOf(20F, 50F, 100F, 70F, 20F, 50F, 100F, 70F),
                isDonut = true,
                valueTextColor = Color.Black,
                onSectionClicked = { percent, value ->
                }
            )
        }
        item {
            Text(
                text = "Donut Chart",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
        }
        item {
            PieChart(
                modifier = Modifier
                    .scale(1f)
                    .size(400.dp)
                    .padding(20.dp),
                data = listOf(20F, 50F, 100F, 70F, 20F, 50F, 100F, 70F),
                isDonut = false,
                valueTextColor = Color.Black,
                onSectionClicked = { percent, value ->
                }
            )
        }
        item {
            Text(
                text = "Pie Chart",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

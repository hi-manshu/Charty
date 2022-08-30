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
import com.himanshoe.charty.pie.config.PieConfig
import com.himanshoe.charty.pie.config.PieData

@Composable
fun PieChartDemo() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val pieData = listOf(PieData(20F), PieData(50F), PieData(100F), PieData(70F), PieData(20F), PieData(50F), PieData(100F), PieData(20F))
        val pieDataWithCustomColors = listOf(PieData(20F, Color(0xFFfafa6e)), PieData(50F, Color(0xFFc4ec74)))

        item {
            PieChart(
                modifier = Modifier
                    .scale(1f)
                    .size(400.dp)
                    .padding(20.dp),
                pieData = pieData,
                config = PieConfig(isDonut = true, expandDonutOnClick = true),
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
                pieData = pieDataWithCustomColors,
                config = PieConfig(isDonut = true, expandDonutOnClick = false),
                valueTextColor = Color.Black,
                onSectionClicked = { percent, value ->
                }
            )
        }
        item {
            Text(
                text = "Donut Chart custom colors, no expand",
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
                pieData = pieData,
                config = PieConfig(false),
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

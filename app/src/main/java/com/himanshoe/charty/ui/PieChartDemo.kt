package com.himanshoe.charty.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

        val pieData = listOf(
            PieData(3F),
            PieData(1F),
            PieData(1F),
        )

        item {
            PieChart(
                modifier = Modifier
                    .fillMaxSize(),
                pieData = pieData,
                config = PieConfig(isDonut = true, expandDonutOnClick = true),
                onSectionClicked = { percent, value ->
                    Log.d("DSfdsfdsf", percent.toString())
                    Log.d("DSfdsfdsf", value.toString())
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
                    .fillMaxSize(),
                pieData = pieData,
                config = PieConfig(isDonut = false, expandDonutOnClick = true),
                onSectionClicked = { percent, value ->
                }
            )
        }
        item {
            Text(
                text = "Pie Chart",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

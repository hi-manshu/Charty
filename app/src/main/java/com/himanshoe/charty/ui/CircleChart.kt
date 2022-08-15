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
import com.himanshoe.charty.circle.CircleChart
import com.himanshoe.charty.circle.model.CircleData

val colors = listOf(Color(0xFFFDC830), Color(0xFFF37335))

@Composable
fun CircleChartDemo() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            CircleChart(
                modifier = Modifier
                    .scale(1f)
                    .size(400.dp)
                    .padding(20.dp),
                circleData = listOf(
                    CircleData(10F, 235F, color = Color(0xFFfafa6e)),
                    CircleData(10F, 135F, color = Color(0xFFc4ec74)),
                    CircleData(10F, 315F, color = Color(0xFF92dc7e)),
                    CircleData(20F, 50F, color = Color(0xFF64c987)),
                    CircleData(30F, 315F, color = Color(0xFF39b48e))
                ),
                isAnimated = false
            )
        }
        item {
            Text(
                text = "CircleChart with individual colors",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }
        item {
            CircleChart(
                modifier = Modifier
                    .scale(1f)
                    .size(400.dp)
                    .padding(20.dp),
                circleData = listOf(
                    CircleData(10F, 235F, Color.Magenta),
                    CircleData(10F, 135F, Color.Red),
                    CircleData(10F, 315F, Color.Green),
                    CircleData(20F, 50F, Color.Gray),
                    CircleData(30F, 315F)
                ),
                color = Color.Yellow
            )
        }
        item {
            Text(
                text = "CircleChart with optional colors",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }
        item {
            CircleChart(
                modifier = Modifier
                    .scale(1f)
                    .size(400.dp)
                    .padding(20.dp),
                circleData = listOf(
                    CircleData(10F, 235F),
                    CircleData(10F, 135F),
                    CircleData(10F, 315F),
                    CircleData(20F, 50F),
                    CircleData(30F, 315F)
                ),
                colors = colors,
                isAnimated = false
            )
        }
        item {
            Text(
                text = "CircleChart with one gradient color shade",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

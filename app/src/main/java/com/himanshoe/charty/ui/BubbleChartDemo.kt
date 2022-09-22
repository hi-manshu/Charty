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
import com.himanshoe.charty.bubble.BubbleChart
import com.himanshoe.charty.bubble.model.BubbleData

val bubbleData = listOf(
    BubbleData(10F, 35F, 70F),
    BubbleData(10F, 15F, 80F),
    BubbleData(10F, 55F, 90F),
    BubbleData(10F, 35F, 35F),
    BubbleData(10F, 15F, 15F),
    BubbleData(10F, 55F, 5F),
    BubbleData(10F, 35F, 70F),
    BubbleData(10F, 15F, 80F),
    BubbleData(10F, 55F, 90F),
    BubbleData(10F, 35F, 35F),
    BubbleData(10F, 15F, 15F),
    BubbleData(10F, 55F, 5F),
)

@Composable
fun BubbleChartDemo() {
    LazyColumn(
        Modifier
            .fillMaxSize()
    ) {
        item {
            BubbleChart(
                bubbleData = bubbleData,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(32.dp),
                color = pcolors.first()
            )
        }
        item {
            Text(
                text = "Bubble Line Graph with Solid Color",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }
        item {
            BubbleChart(
                bubbleData = bubbleData,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(32.dp),
                colors = colors
            )
        }
        item {
            Text(
                text = "Bubble Line Graph with Gradient Color",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

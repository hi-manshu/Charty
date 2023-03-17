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
import com.himanshoe.charty.linearregression.LinearRegressionChart
import com.himanshoe.charty.linearregression.config.LinearRegressionConfig
import com.himanshoe.charty.linearregression.model.LinearRegressionData
import com.himanshoe.charty.point.cofig.PointType

@Composable
fun LinearRegressionChartDemo() {
    LazyColumn(
        Modifier
            .fillMaxSize()
    ) {
        item {
            LinearRegressionChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 300.dp)
                    .padding(20.dp),
                scatterColors = colors.last(),
                lineColors = colors.first(),
                linearRegressionConfig = LinearRegressionConfig(
                    pointType = PointType.Fill,
                    strokeSize = 2.dp
                ),
                linearRegressionData = listOf(
                    LinearRegressionData(xValue = 3986.37, yPointValue = 128.25f, yLineValue = 128.0075f),
                    LinearRegressionData(xValue = 3992.01, yPointValue = 128.05f, yLineValue = 128.1305f),
                    LinearRegressionData(xValue = 3918.32, yPointValue = 126.16f, yLineValue = 126.5237f),
                    LinearRegressionData(xValue = 3861.59, yPointValue = 125.45f, yLineValue = 125.2867f),
                    LinearRegressionData(xValue = 3884.29, yPointValue = 125.82f, yLineValue = 125.7817f),
                )
            )
        }
        item {
            Text(
                text = "Linear Regression Chart",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
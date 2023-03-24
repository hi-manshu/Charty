package com.himanshoe.charty.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himanshoe.charty.common.label.Multiplier
import com.himanshoe.charty.common.label.XLabels
import com.himanshoe.charty.common.label.YLabels
import com.himanshoe.charty.linearregression.LinearRegressionChart
import com.himanshoe.charty.linearregression.config.LinearRegressionConfig
import com.himanshoe.charty.linearregression.model.LinearRegressionData
import com.himanshoe.charty.point.cofig.PointType

@Composable
fun LinearRegressionChartDemo() {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                yLabelConfig = YLabels(
                    fontColor = Color.Red,
                    fontSize = 24f,
                    rangeAdjustment = Multiplier(.1f),
                    minValueAdjustment = Multiplier(.1f),
                    breaks = 10,
                    rotation = -45f,
                    lineAlpha = .9f
                ),
                xLabelConfig = XLabels(
                    fontColor = Color.Blue,
                    fontSize = 24f,
                    rangeAdjustment = Multiplier(.1f),
                    breaks = 7,
                    rotation = 60f,
                    showLines = true
                ),
                linearRegressionData = listOf(
                    LinearRegressionData(xValue = 3986.37f, yValue = 128.25f),
                    LinearRegressionData(xValue = 3992.01f, yValue = 128.05f),
                    LinearRegressionData(xValue = 3918.32f, yValue = 126.16f),
                    LinearRegressionData(xValue = 3861.59f, yValue = 125.45f),
                    LinearRegressionData(xValue = 3884.29f, yValue = 125.82f),
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
                textAlign = TextAlign.Center,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        }
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
                    strokeSize = 2.dp,
                    pointSize = 2.dp
                ),
                yLabelConfig = YLabels(
                    rotation = 0f,
                    lineAlpha = .9f
                ),
                xLabelConfig = XLabels(
                    rotation = 45f,
                    showLines = true
                ),
                linearRegressionData = listOf(
                    LinearRegressionData(xValue = 3986.37f, yValue = 128.25f),
                    LinearRegressionData(xValue = 3992.01f, yValue = 128.05f),
                    LinearRegressionData(xValue = 3918.32f, yValue = 126.16f),
                    LinearRegressionData(xValue = 3861.59f, yValue = 125.45f),
                    LinearRegressionData(xValue = 3884.29f, yValue = 125.82f),
                )
            )
        }
        item {
            Text(
                text = "Linear Regression Chart with\nadjusted point size",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        }

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
                yLabelConfig = YLabels(
                    rotation = -30f,
                    lineAlpha = .9f
                ),
                xLabelConfig = XLabels(
                    rotation = 30f,
                    showLines = true
                ),
                linearRegressionData = listOf(
                    LinearRegressionData(10F, 35F),
                    LinearRegressionData(20F, 25F),
                    LinearRegressionData(10F, 50F),
                    LinearRegressionData(100F, 10F),
                    LinearRegressionData(10F, 15F),
                    LinearRegressionData(50F, 100F),
                    LinearRegressionData(20F, 25F),
                )
            )
        }
        item {
            Text(
                text = "Linear Regression Chart with\nduplicate x values",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        }
    }
}

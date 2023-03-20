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
                    breaks = 10
                ),
                xLabelConfig = XLabels(
                    fontColor = Color.Blue,
                    fontSize = 24f,
                    rangeAdjustment = Multiplier(.1f),
                    breaks = 7
                ),
                linearRegressionData = listOf(
                    LinearRegressionData(xValue = 3986.37f, yPointValue = 128.25f, yLineValue = 128f),
                    LinearRegressionData(xValue = 3992.01f, yPointValue = 128.05f, yLineValue = 128.13f),
                    LinearRegressionData(xValue = 3918.32f, yPointValue = 126.16f, yLineValue = 126.52f),
                    LinearRegressionData(xValue = 3861.59f, yPointValue = 125.45f, yLineValue = 125.29f),
                    LinearRegressionData(xValue = 3884.29f, yPointValue = 125.82f, yLineValue = 125.78f),
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
                linearRegressionData = listOf(
                    LinearRegressionData(xValue = 3986.37f, yPointValue = 128.25f, yLineValue = 128f),
                    LinearRegressionData(xValue = 3992.01f, yPointValue = 128.05f, yLineValue = 128.13f),
                    LinearRegressionData(xValue = 3918.32f, yPointValue = 126.16f, yLineValue = 126.52f),
                    LinearRegressionData(xValue = 3861.59f, yPointValue = 125.45f, yLineValue = 125.29f),
                    LinearRegressionData(xValue = 3884.29f, yPointValue = 125.82f, yLineValue = 125.78f),
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
                linearRegressionData = listOf(
                    LinearRegressionData(10F, 35F, 35f),
                    LinearRegressionData(20F, 25F, 25f),
                    LinearRegressionData(10F, 50F, 50f),
                    LinearRegressionData(100F, 10F, 10f),
                    LinearRegressionData(10F, 15F, 15f),
                    LinearRegressionData(50F, 100F, 100f),
                    LinearRegressionData(20F, 25F, 25f),
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
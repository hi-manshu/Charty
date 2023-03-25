@file:Suppress("DuplicatedCode")

package com.himanshoe.charty.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    var regressionConfig by remember { mutableStateOf(LinearRegressionConfig()) }
    var yLabels by remember { mutableStateOf(YLabels()) }
    var xLabels by remember { mutableStateOf(XLabels()) }

    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            Text(
                text = "Linear Regression Chart",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
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
                scatterColor = colors.last(),
                lineColor = colors.first(),
                linearRegressionConfig = LinearRegressionConfig(
                    pointType = regressionConfig.pointType,
                    strokeSize = regressionConfig.strokeSize,
                    pointSize = regressionConfig.pointSize
                ),
                yLabelConfig = YLabels(
                    fontColor = yLabels.fontColor,
                    fontSize = yLabels.fontSize,
                    rangeAdjustment = yLabels.rangeAdjustment,
                    minValueAdjustment = yLabels.minValueAdjustment,
                    breaks = yLabels.breaks,
                    rotation = yLabels.rotation,
                    lineAlpha = yLabels.lineAlpha,
                    maxValueAdjustment = yLabels.maxValueAdjustment,
                    isBaseZero = yLabels.isBaseZero,
                    xOffset = yLabels.xOffset,
                    yOffset = yLabels.yOffset,
                    textAlignment = yLabels.textAlignment,
                    lineStartPadding = yLabels.lineStartPadding
                ),
                xLabelConfig = XLabels(
                    fontColor = xLabels.fontColor,
                    fontSize = xLabels.fontSize,
                    rangeAdjustment = xLabels.rangeAdjustment,
                    breaks = xLabels.breaks,
                    yOffset = xLabels.yOffset,
                    xOffset = xLabels.xOffset,
                    textAlignment = xLabels.textAlignment,
                    rotation = xLabels.rotation,
                    lineAlpha = xLabels.lineAlpha,
                    showLines = xLabels.showLines
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
            var pointSwitch by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text("Figure Configs", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)

                Spacer(Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val value = if (pointSwitch) "Fill" else "Stroke"
                    Text("Point Type: $value")
                    Switch(
                        checked = pointSwitch,
                        onCheckedChange = {
                            regressionConfig = regressionConfig.copy(pointType = if (it) PointType.Fill else PointType.Stroke)
                            pointSwitch = it
                        }
                    )
                }

                Spacer(Modifier.height(15.dp))

                Text("Stroke Size: ${regressionConfig.strokeSize.value.toInt()}.dp")
                Spacer(Modifier.height(2.5.dp))
                Slider(
                    value = regressionConfig.strokeSize.value,
                    onValueChange = {
                        regressionConfig = regressionConfig.copy(strokeSize = it.dp)
                    },
                    valueRange = 1f..20f
                )

                Spacer(Modifier.height(5.dp))

                Text("Point Size: ${regressionConfig.pointSize.value.toInt()}.dp")
                Spacer(Modifier.height(2.5.dp))
                Slider(
                    value = regressionConfig.pointSize.value,
                    onValueChange = {
                        regressionConfig = regressionConfig.copy(pointSize = it.dp)
                    },
                    valueRange = 1f..20f
                )
            }
        }
        item {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text("Y-Axis Configs", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)

                Spacer(Modifier.height(15.dp))

                Text("Font Size: ${yLabels.fontSize}")
                Spacer(Modifier.height(2.5.dp))
                Slider(
                    value = yLabels.fontSize,
                    onValueChange = {
                        yLabels = yLabels.copy(fontSize = it)
                    },
                    valueRange = 1f..50f
                )

                Spacer(Modifier.height(5.dp))

                Text("X Offset: ${yLabels.xOffset}")
                Spacer(Modifier.height(2.5.dp))
                Slider(
                    value = yLabels.xOffset,
                    onValueChange = {
                        yLabels = yLabels.copy(xOffset = it)
                    },
                    valueRange = 1f..100f
                )

                Spacer(Modifier.height(5.dp))

                Text("Rotation: ${yLabels.rotation}")
                Spacer(Modifier.height(2.5.dp))
                Slider(
                    value = yLabels.rotation,
                    onValueChange = {
                        yLabels = yLabels.copy(rotation = it)
                    },
                    valueRange = -90f..0f
                )

                Spacer(Modifier.height(5.dp))

                Text("Breaks: ${yLabels.breaks}")
                Spacer(Modifier.height(2.5.dp))
                Slider(
                    value = yLabels.breaks.toFloat(),
                    onValueChange = {
                        yLabels = yLabels.copy(breaks = it.toInt())
                    },
                    valueRange = 1f..15f
                )

                Spacer(Modifier.height(5.dp))

                Text("Adjusted Range: ${yLabels.rangeAdjustment.factor}")
                Spacer(Modifier.height(2.5.dp))
                Slider(
                    value = yLabels.rangeAdjustment.factor,
                    onValueChange = {
                        yLabels = yLabels.copy(rangeAdjustment = Multiplier(it))
                    },
                    valueRange = 0f..1f
                )

                Spacer(Modifier.height(5.dp))

                Text("Adjusted Min: ${yLabels.minValueAdjustment.factor}")
                Spacer(Modifier.height(2.5.dp))
                Slider(
                    value = yLabels.minValueAdjustment.factor,
                    onValueChange = {
                        yLabels = yLabels.copy(minValueAdjustment = Multiplier(it))
                    },
                    valueRange = 0f..1f
                )

                Spacer(Modifier.height(5.dp))

                Text("Adjusted Max: ${yLabels.maxValueAdjustment.factor}")
                Spacer(Modifier.height(2.5.dp))
                Slider(
                    value = yLabels.maxValueAdjustment.factor,
                    onValueChange = {
                        yLabels = yLabels.copy(maxValueAdjustment = Multiplier(it))
                    },
                    valueRange = 0f..1f
                )
            }
        }
        item {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text("X-Axis Configs", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)

                Spacer(Modifier.height(15.dp))

                Text("Font Size: ${xLabels.fontSize}")
                Spacer(Modifier.height(2.5.dp))
                Slider(
                    value = xLabels.fontSize,
                    onValueChange = {
                        xLabels = xLabels.copy(fontSize = it)
                    },
                    valueRange = 1f..50f
                )

                Spacer(Modifier.height(5.dp))

                Text("Y Offset: ${xLabels.yOffset}")
                Spacer(Modifier.height(2.5.dp))
                Slider(
                    value = xLabels.yOffset,
                    onValueChange = {
                        xLabels = xLabels.copy(yOffset = it)
                    },
                    valueRange = 1f..100f
                )

                Spacer(Modifier.height(5.dp))

                Text("Rotation: ${xLabels.rotation}")
                Spacer(Modifier.height(2.5.dp))
                Slider(
                    value = xLabels.rotation,
                    onValueChange = {
                        xLabels = xLabels.copy(rotation = it)
                    },
                    valueRange = 0f..90f
                )

                Spacer(Modifier.height(5.dp))

                Text("Breaks: ${xLabels.breaks}")
                Spacer(Modifier.height(2.5.dp))
                Slider(
                    value = xLabels.breaks.toFloat(),
                    onValueChange = {
                        xLabels = xLabels.copy(breaks = it.toInt())
                    },
                    valueRange = 1f..15f
                )

                Spacer(Modifier.height(5.dp))

                Text("Adjusted Range: ${xLabels.rangeAdjustment.factor}")
                Spacer(Modifier.height(2.5.dp))
                Slider(
                    value = xLabels.rangeAdjustment.factor,
                    onValueChange = {
                        xLabels = xLabels.copy(rangeAdjustment = Multiplier(it))
                    },
                    valueRange = 0f..1f
                )
            }
        }
    }
}

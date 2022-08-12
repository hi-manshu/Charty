package com.himanshoe.charty.bar

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.himanshoe.charty.bar.common.calculations.getTopLeft
import com.himanshoe.charty.bar.common.calculations.getTopRight
import com.himanshoe.charty.bar.common.component.drawBarLabel
import com.himanshoe.charty.bar.config.BarConfig
import com.himanshoe.charty.bar.config.BarConfigDefaults
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.bar.model.GroupedBarData
import com.himanshoe.charty.bar.model.maxYValue
import com.himanshoe.charty.bar.model.totalItems
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.axis.xAxis
import com.himanshoe.charty.common.axis.yAxis
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults

@Composable
fun GroupedBarChart(
    groupedBarData: List<GroupedBarData>,
    modifier: Modifier = Modifier,
    onBarClick: (BarData) -> Unit = {},
    barDimens: ChartDimens = ChartDimensDefaults.chartDimensDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    barConfig: BarConfig = BarConfigDefaults.barConfigDimesDefaults()
) {
    val barWidth = remember { mutableStateOf(0F) }
    val maxYValueState = rememberSaveable { mutableStateOf(groupedBarData.maxYValue()) }
    val clickedBar = remember {
        mutableStateOf(Offset(-10F, -10F))
    }
    val maxYValue = maxYValueState.value

    val totalItems: Int = groupedBarData.totalItems()
    Canvas(modifier = modifier
        .drawBehind {
            if (axisConfig.showAxes) {
                xAxis(axisConfig, maxYValue)
                yAxis(axisConfig)
            }
        }
        .padding(horizontal = barDimens.horizontalPadding)
        .pointerInput(Unit) {
            detectTapGestures(onPress = { offset ->
                clickedBar.value = offset
            })
        }
    ) {
        barWidth.value = size.width.div(totalItems.times(1.2F))
        val yScalableFactor = size.height.div(maxYValue)
        val groupedBarDataColor: List<Color> = groupedBarData.flatMap { it.colors }
        val groupedBarDataCount = groupedBarData.flatMap { it.barData }.count()

        if (groupedBarDataColor.count() != groupedBarDataCount) throw Exception("Total colors cannot be more then $groupedBarDataCount")

        groupedBarData.flatMap { it.barData }
            .forEachIndexed { index, data ->
                val topLeft = getTopLeft(index, barWidth, size, data, yScalableFactor)
                val topRight = getTopRight(index, barWidth, size, data, yScalableFactor)
                val barHeight = data.yValue.times(yScalableFactor)

                if (clickedBar.value.x in (topLeft.x..topRight.x)) {
                    onBarClick(data)
                }
                drawRoundRect(
                    cornerRadius = CornerRadius(if (barConfig.hasRoundedCorner) barHeight else 0F),
                    topLeft = topLeft,
                    color = groupedBarDataColor[index],
                    size = Size(barWidth.value, barHeight)
                )
                // draw label
                drawBarLabel(data, barWidth.value, barHeight, topLeft)
            }
    }
}

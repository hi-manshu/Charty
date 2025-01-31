package com.himanshoe.charty.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.bar.model.StorageData

/**
 * A composable function that displays a storage bar with a single track color.
 *
 * @param data A list of StorageData representing the categories and their values.
 * @param trackColor The color of the track. Default is a light gray color.
 * @param modifier The modifier to be applied to the Canvas.
 * @param onClick A lambda function to be invoked when a category is clicked.
 */
@Composable
fun StorageBar(
    data: () -> List<StorageData>,
    trackColor: Color = Color(0xD3D3D3DE),
    modifier: Modifier = Modifier,
    onClick: (StorageData) -> Unit = {}
) {
    StorageBarContent(
        data = data,
        trackColor = SolidColor(trackColor),
        modifier = modifier,
        onClick = onClick
    )
}

/**
 * A composable function that displays a storage bar with a gradient track color.
 *
 * @param data A list of StorageData representing the categories and their values.
 * @param trackColors A list of colors to be used in the gradient of the track.
 * @param modifier The modifier to be applied to the Canvas.
 * @param onClick A lambda function to be invoked when a category is clicked.
 */
@Composable
fun StorageBar(
    data: () -> List<StorageData>,
    trackColors: List<Color>,
    modifier: Modifier = Modifier,
    onClick: (StorageData) -> Unit = {}
) {
    StorageBarContent(
        data = data,
        trackColor = Brush.linearGradient(trackColors),
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
private fun StorageBarContent(
    data: ()->List<StorageData>,
    trackColor: Brush,
    modifier: Modifier = Modifier,
    onClick: (StorageData) -> Unit = {}
) {
    var clickedOffSet by mutableStateOf<Offset?>(null)
    var clickedBarIndex by mutableIntStateOf(-1)
    Canvas(modifier = modifier.fillMaxWidth()
        .pointerInput(data) {
            detectTapGestures { offset -> clickedOffSet = offset }
        }
    ) {
        val totalWidth = size.width
        val totalHeight = size.height
        var currentOffset = 0f
        val categoryWidths = data().map { it.value * totalWidth }

        data().fastForEachIndexed { index, category ->
            val categoryWidth = categoryWidths[index]
            val isFirst = index == 0
            val additionalHeight = if (clickedBarIndex == index) totalHeight * 0.05F else 0F
            val size = Size(
                width = categoryWidth,
                height = totalHeight + additionalHeight
            )
            val path = calculatePath(
                offset = Offset(x = currentOffset, y = -additionalHeight / 2),
                size = size,
                isFirst = isFirst
            )
            clickedOffSet?.x?.let {
                if (it in currentOffset..(currentOffset + categoryWidth)) {
                    clickedBarIndex = index
                    onClick(category)
                }
            }
            drawPath(path = path, brush = SolidColor(category.color))
            currentOffset += categoryWidth
        }

        if (currentOffset < totalWidth) {
            val backgroundPath = calculatePath(
                offset = Offset(x = currentOffset, y = 0f),
                size = Size(width = totalWidth - currentOffset, height = totalHeight),
                isLast = true
            )
            drawPath(
                path = backgroundPath,
                brush = trackColor
            )
        }

    }
}

private fun calculatePath(
    offset: Offset,
    size: Size,
    isFirst: Boolean = false,
    isLast: Boolean = false
): Path {
    val cornerRadius = CornerRadius(10F, 10F)
    return Path().apply {
        addRoundRect(
            RoundRect(
                rect = Rect(offset = offset, size = size),
                topLeft = if (isFirst) cornerRadius else CornerRadius.Zero,
                topRight = if (isLast) cornerRadius else CornerRadius.Zero,
                bottomLeft = if (isFirst) cornerRadius else CornerRadius.Zero,
                bottomRight = if (isLast) cornerRadius else CornerRadius.Zero,
            )
        )
    }
}
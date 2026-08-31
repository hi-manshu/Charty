package com.himanshoe.charty.snapshot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import com.himanshoe.charty.color.ChartyColor
import com.himanshoe.charty.color.ChartyColors
import com.himanshoe.charty.common.config.Animation
import com.himanshoe.charty.pie.PieChart
import com.himanshoe.charty.pie.config.LabelConfig
import com.himanshoe.charty.pie.config.PieChartConfig
import com.himanshoe.charty.pie.data.PieData
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

private const val PIE_SNAPSHOT_SDK = 34
private const val PIE_ANTIALIASING_TOLERANCE = 0.01f
private const val PIE_SNAPSHOT_SCREEN = "w480dp-h800dp"

/**
 * Pins that `LabelConfig.shouldShowLabelsOutside` moves the slice labels outside the rim.
 *
 * The flag was declared and documented for a release without a single reader in the drawing code —
 * the same defect class as #179's `showValues`. Only a rendering test can hold the fix in place.
 * Outside labels default to a dark style in this capture because the config's white-on-slice default
 * is unreadable on a white background — which is the caller's choice to make, not the chart's.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [PIE_SNAPSHOT_SDK], qualifiers = PIE_SNAPSHOT_SCREEN)
class PieOutsideLabelTest {
    @Test
    fun pieLabelsOutside() = capture(name = "pie_labels_outside", outside = true)

    @Test
    fun pieLabelsInside() = capture(name = "pie_labels_inside", outside = false)

    private fun capture(
        name: String,
        outside: Boolean,
    ) {
        captureRoboImage(
            filePath = "src/androidUnitTest/snapshots/$name.png",
            roborazziOptions =
                RoborazziOptions(
                    compareOptions = RoborazziOptions.CompareOptions(changeThreshold = PIE_ANTIALIASING_TOLERANCE),
                ),
        ) {
            PieUnderTest(outside = outside)
        }
    }
}

@Composable
private fun PieUnderTest(outside: Boolean) {
    Box(modifier = Modifier.size(size = 360.dp).background(color = Color.White)) {
        PieChart(
            data = {
                listOf(
                    PieData(label = "A", value = 45f, color = ChartyColor.Solid(ChartyColors.Blue)),
                    PieData(label = "B", value = 30f, color = ChartyColor.Solid(ChartyColors.Teal)),
                    PieData(label = "C", value = 15f, color = ChartyColor.Solid(ChartyColors.Orange)),
                    PieData(label = "D", value = 10f, color = ChartyColor.Solid(ChartyColors.Purple)),
                )
            },
            config =
                PieChartConfig(
                    animation = Animation.Disabled,
                    labelConfig =
                        LabelConfig(
                            shouldShowLabelsOutside = outside,
                            labelTextStyle =
                                if (outside) {
                                    TextStyle(color = Color.Black, fontSize = 12.sp)
                                } else {
                                    LabelConfig().labelTextStyle
                                },
                        ),
                ),
        )
    }
}

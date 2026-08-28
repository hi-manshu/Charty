package com.himanshoe.charty.snapshot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import com.himanshoe.charty.color.ChartyColor
import com.himanshoe.charty.color.ChartyColors
import com.himanshoe.charty.common.config.Animation
import com.himanshoe.charty.radar.RadarChart
import com.himanshoe.charty.radar.config.RadarChartConfig
import com.himanshoe.charty.radar.config.RadarLabelConfig
import com.himanshoe.charty.radar.data.RadarAxisData
import com.himanshoe.charty.radar.data.RadarDataSet
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

private const val RADAR_SNAPSHOT_SDK = 34
private const val RADAR_ANTIALIASING_TOLERANCE = 0.01f

/**
 * Pins that `RadarLabelConfig.showValues` puts numbers on the chart.
 *
 * It had never drawn anything. The property and its `valueTextStyle` companion were declared,
 * documented and read by nothing, so a caller who asked for values got labels and no error — which is
 * how it was reported, in #179.
 *
 * Rendering is the only test that can catch this class of bug: the code compiled perfectly well while
 * doing nothing, and any assertion short of "did text appear" would have compiled too.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [RADAR_SNAPSHOT_SDK])
class RadarValueLabelTest {
    @Test
    fun radarValuesShown() = capture(name = "radar_values_shown", showValues = true)

    @Test
    fun radarValuesHidden() = capture(name = "radar_values_hidden", showValues = false)

    private fun capture(
        name: String,
        showValues: Boolean,
    ) {
        captureRoboImage(
            filePath = "src/androidUnitTest/snapshots/$name.png",
            roborazziOptions =
                RoborazziOptions(
                    compareOptions = RoborazziOptions.CompareOptions(changeThreshold = RADAR_ANTIALIASING_TOLERANCE),
                ),
        ) {
            RadarUnderTest(showValues = showValues)
        }
    }
}

@Composable
private fun RadarUnderTest(showValues: Boolean) {
    Box(modifier = Modifier.size(width = 360.dp, height = 320.dp).background(color = Color.White)) {
        RadarChart(
            data = {
                listOf(
                    RadarDataSet(
                        label = "Muscle Groups",
                        axes =
                            listOf(
                                RadarAxisData(label = "Chest", value = 80f, maxValue = 100f),
                                RadarAxisData(label = "Back", value = 65f, maxValue = 100f),
                                RadarAxisData(label = "Legs", value = 92f, maxValue = 100f),
                                RadarAxisData(label = "Arms", value = 40f, maxValue = 100f),
                                RadarAxisData(label = "Core", value = 55f, maxValue = 100f),
                            ),
                        color = ChartyColor.Solid(ChartyColors.Blue),
                    ),
                )
            },
            config =
                RadarChartConfig(
                    animation = Animation.Disabled,
                    labelConfig = RadarLabelConfig(showLabels = true, showValues = showValues),
                ),
        )
    }
}

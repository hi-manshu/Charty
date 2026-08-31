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
import com.himanshoe.charty.radar.MultipleRadarChart
import com.himanshoe.charty.radar.RadarChart
import com.himanshoe.charty.radar.config.MultipleRadarChartConfig
import com.himanshoe.charty.radar.config.RadarChartConfig
import com.himanshoe.charty.radar.config.RadarLabelConfig
import com.himanshoe.charty.radar.config.RadarValuePlacement
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
 * A screen wide enough to hold the widest test composable. Robolectric's default device is 320dp
 * across, and a capture silently clips at the screen edge - the first recording of these goldens
 * lost 40dp off the right side and nothing failed.
 */
private const val RADAR_SNAPSHOT_SCREEN = "w480dp-h800dp"

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
@Config(sdk = [RADAR_SNAPSHOT_SDK], qualifiers = RADAR_SNAPSHOT_SCREEN)
class RadarValueLabelTest {
    @Test
    fun radarValuesShown() = capture(name = "radar_values_shown", showValues = true)

    @Test
    fun radarValuesHidden() = capture(name = "radar_values_hidden", showValues = false)

    @Test
    fun radarValuesBelowLabels() {
        captureRoboImage(
            filePath = "src/androidUnitTest/snapshots/radar_values_below_label.png",
            roborazziOptions =
                RoborazziOptions(
                    compareOptions = RoborazziOptions.CompareOptions(changeThreshold = RADAR_ANTIALIASING_TOLERANCE),
                ),
        ) {
            RadarWithSparseData()
        }
    }

    @Test
    fun radarValuesBelowHiddenLabels() {
        captureRoboImage(
            filePath = "src/androidUnitTest/snapshots/radar_values_below_hidden_labels.png",
            roborazziOptions =
                RoborazziOptions(
                    compareOptions = RoborazziOptions.CompareOptions(changeThreshold = RADAR_ANTIALIASING_TOLERANCE),
                ),
        ) {
            RadarWithHiddenLabels()
        }
    }

    @Test
    fun multipleRadarValuesBelowLabels() {
        captureRoboImage(
            filePath = "src/androidUnitTest/snapshots/multiple_radar_values_below_label.png",
            roborazziOptions =
                RoborazziOptions(
                    compareOptions = RoborazziOptions.CompareOptions(changeThreshold = RADAR_ANTIALIASING_TOLERANCE),
                ),
        ) {
            MultipleRadarUnderTest()
        }
    }

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

/**
 * The data shape from #179: five zeros and one real value. At the vertices, five of these six values
 * sit on the centre point, because a zero's vertex is the centre — which is why this placement
 * exists. The golden pins that every value reads at the rim beside its axis name instead.
 */
@Composable
private fun RadarWithSparseData() {
    Box(modifier = Modifier.size(width = 360.dp, height = 400.dp).background(color = Color.White)) {
        RadarChart(
            data = {
                listOf(
                    RadarDataSet(
                        label = "Muscle Groups",
                        axes =
                            listOf(
                                RadarAxisData(label = "Back", value = 0f, maxValue = 54f),
                                RadarAxisData(label = "Shoulders", value = 0f, maxValue = 54f),
                                RadarAxisData(label = "Core", value = 0f, maxValue = 54f),
                                RadarAxisData(label = "Arms", value = 54f, maxValue = 54f),
                                RadarAxisData(label = "Chest", value = 0f, maxValue = 54f),
                                RadarAxisData(label = "Legs", value = 0f, maxValue = 54f),
                            ),
                        color = ChartyColor.Solid(ChartyColors.Purple),
                    ),
                )
            },
            config =
                RadarChartConfig(
                    animation = Animation.Disabled,
                    showDataPoints = false,
                    labelConfig =
                        RadarLabelConfig(
                            showLabels = true,
                            showValues = true,
                            valuePlacement = RadarValuePlacement.BELOW_AXIS_LABEL,
                        ),
                ),
        )
    }
}

@Composable
private fun MultipleRadarUnderTest() {
    Box(modifier = Modifier.size(width = 440.dp, height = 400.dp).background(color = Color.White)) {
        MultipleRadarChart(
            dataSets = {
                listOf(
                    RadarDataSet(
                        label = "This week",
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
                    RadarDataSet(
                        label = "Last week",
                        axes =
                            listOf(
                                RadarAxisData(label = "Chest", value = 60f, maxValue = 100f),
                                RadarAxisData(label = "Back", value = 75f, maxValue = 100f),
                                RadarAxisData(label = "Legs", value = 70f, maxValue = 100f),
                                RadarAxisData(label = "Arms", value = 55f, maxValue = 100f),
                                RadarAxisData(label = "Core", value = 45f, maxValue = 100f),
                            ),
                        color = ChartyColor.Solid(ChartyColors.Orange),
                    ),
                )
            },
            config =
                MultipleRadarChartConfig(
                    radarConfig =
                        RadarChartConfig(
                            animation = Animation.Disabled,
                            paddingFraction = 0.22f,
                            labelConfig =
                                RadarLabelConfig(
                                    showLabels = true,
                                    showValues = true,
                                    valuePlacement = RadarValuePlacement.BELOW_AXIS_LABEL,
                                ),
                        ),
                ),
        )
    }
}

/**
 * Labels off, values still below-label placed: each value centres itself on the spot its label would
 * have occupied. The centring maths once sat half a gap low — review caught it, this golden keeps it
 * caught.
 */
@Composable
private fun RadarWithHiddenLabels() {
    Box(modifier = Modifier.size(width = 360.dp, height = 400.dp).background(color = Color.White)) {
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
                    labelConfig =
                        RadarLabelConfig(
                            showLabels = false,
                            showValues = true,
                            valuePlacement = RadarValuePlacement.BELOW_AXIS_LABEL,
                        ),
                ),
        )
    }
}

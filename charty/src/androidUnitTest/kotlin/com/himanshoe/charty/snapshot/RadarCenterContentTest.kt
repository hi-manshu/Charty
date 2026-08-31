package com.himanshoe.charty.snapshot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import com.himanshoe.charty.color.ChartyColor
import com.himanshoe.charty.color.ChartyColors
import com.himanshoe.charty.common.config.Animation
import com.himanshoe.charty.radar.RadarChart
import com.himanshoe.charty.radar.config.RadarCenterConfig
import com.himanshoe.charty.radar.config.RadarChartConfig
import com.himanshoe.charty.radar.data.RadarAxisData
import com.himanshoe.charty.radar.data.RadarDataSet
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

private const val CENTER_SNAPSHOT_SDK = 34
private const val CENTER_ANTIALIASING_TOLERANCE = 0.01f

/**
 * Pins the `centerContent` slot: a composable handed to the radar renders over its centre, above the
 * backdrop circle from [RadarCenterConfig.centerBackgroundRadius]. The slot replaces the deprecated
 * `showCenterIcon` flag, which shipped without any icon to show or a way to supply one.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [CENTER_SNAPSHOT_SDK])
class RadarCenterContentTest {
    @Test
    fun radarCenterContent() {
        captureRoboImage(
            filePath = "src/androidUnitTest/snapshots/radar_center_content.png",
            roborazziOptions =
                RoborazziOptions(
                    compareOptions = RoborazziOptions.CompareOptions(changeThreshold = CENTER_ANTIALIASING_TOLERANCE),
                ),
        ) {
            RadarWithCenterScore()
        }
    }
}

@Composable
private fun RadarWithCenterScore() {
    Box(modifier = Modifier.size(size = 320.dp).background(color = Color.White)) {
        RadarChart(
            data = {
                listOf(
                    RadarDataSet(
                        label = "Player",
                        axes =
                            listOf(
                                RadarAxisData(label = "Speed", value = 80f),
                                RadarAxisData(label = "Power", value = 65f),
                                RadarAxisData(label = "Skill", value = 90f),
                                RadarAxisData(label = "Stamina", value = 70f),
                                RadarAxisData(label = "Defense", value = 55f),
                            ),
                        color = ChartyColor.Solid(ChartyColors.Blue),
                    ),
                )
            },
            config =
                RadarChartConfig(
                    animation = Animation.Disabled,
                    centerConfig =
                        RadarCenterConfig(
                            centerBackgroundColor = ChartyColor.Solid(Color.White),
                            centerBackgroundRadius = 60f,
                        ),
                ),
            centerContent = {
                Text(
                    text = "72",
                    style = TextStyle(color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold),
                )
            },
        )
    }
}

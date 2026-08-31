@file:Suppress(
    "MagicNumber",
    "LongMethod",
    "FunctionNaming",
    "UndocumentedPublicFunction",
    "MaxLineLength",
    "ktlint:standard:max-line-length",
    "ktlint:standard:function-naming",
)

package com.himanshoe.sample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.himanshoe.charty.color.ChartyColor
import com.himanshoe.charty.radar.MultipleRadarChart
import com.himanshoe.charty.radar.config.MultipleRadarChartConfig
import com.himanshoe.charty.radar.config.RadarChartConfig
import com.himanshoe.charty.radar.config.RadarLabelConfig
import com.himanshoe.charty.radar.config.RadarValuePlacement
import com.himanshoe.charty.radar.data.RadarAxisData
import com.himanshoe.charty.radar.data.RadarDataSet
import kotlin.random.Random

private val SKILLS = listOf("Speed", "Power", "Range", "Control", "Stamina", "Accuracy")

private fun radarSets(
    tick: Int,
    count: Int,
): List<RadarDataSet> {
    val random = Random(seed = tick * 41 + 13)
    return List(count) { index ->
        RadarDataSet(
            label = listOf("Baseline", "Current", "Target")[index],
            axes = SKILLS.map { skill -> RadarAxisData(label = skill, value = 25f + random.nextFloat() * 70f) },
            color = ChartyColor.Solid(playgroundPalette[index]),
        )
    }
}

/**
 * Interactive demo for [MultipleRadarChart]: several profiles overlaid on one set of axes, which is
 * the comparison a single radar cannot make.
 */
@Composable
internal fun MultipleRadarPlayground() {
    var setCount by remember { mutableIntStateOf(2) }
    var showLegend by remember { mutableStateOf(true) }
    var showValues by remember { mutableStateOf(false) }
    var valuePlacement by remember { mutableStateOf(RadarValuePlacement.DATA_POINT) }
    var lineWidth by remember { mutableIntStateOf(2) }
    var pointRadius by remember { mutableIntStateOf(4) }
    var innerCircle by remember { mutableStateOf(true) }
    var stagger by remember { mutableStateOf(true) }
    var tick by remember { mutableIntStateOf(0) }
    var clicked by remember { mutableStateOf<String?>(null) }

    val dataSets = remember(tick, setCount) { radarSets(tick = tick, count = setCount) }

    val code =
        """
        // Several profiles on one set of axes — the comparison a single radar cannot make.
        MultipleRadarChart(
            dataSets = { dataSets },            // List<RadarDataSet>(label, axes, color)
            config = MultipleRadarChartConfig(
                showLegend = $showLegend,
                radarConfig = RadarChartConfig(
                    paddingFraction = 0.22f,
                    labelConfig = RadarLabelConfig(showValues = $showValues, valuePlacement = RadarValuePlacement.${valuePlacement.name}),
                ),
            ),
            onDataSetClick = { set, index -> /* ${clicked ?: "tap a profile"} */ },
        )
        """.trimIndent()

    PlaygroundScaffold(
        code = code,
        cartesian = false,
        chart = {
            MultipleRadarChart(
                dataSets = { dataSets },
                modifier = Modifier.fillMaxSize(),
                config =
                    MultipleRadarChartConfig(
                        showLegend = showLegend,
                        datasetLineWidth = lineWidth.toFloat(),
                        datasetPointRadius = pointRadius.toFloat(),
                        showPointInnerCircle = innerCircle,
                        staggerAnimation = stagger,
                        radarConfig =
                            RadarChartConfig(
                                paddingFraction = 0.22f,
                                labelConfig =
                                    RadarLabelConfig(
                                        showValues = showValues,
                                        valuePlacement = valuePlacement,
                                    ),
                            ),
                    ),
                onDataSetClick = { set, index -> clicked = "${set.label} (#$index)" },
            )
        },
        controls = {
            ControlSection(title = "Profiles")
            IntSliderRow(
                label = "How many",
                value = setCount,
                valueRange = 1..3,
                onValueChange = { setCount = it },
            )
            Text(
                text = "Overlaying profiles is what this chart is for; past three the shapes stop separating and a grouped bar reads better.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            SwitchRow(label = "Legend", checked = showLegend, onCheckedChange = { showLegend = it })
            SwitchRow(label = "Axis values", checked = showValues, onCheckedChange = { showValues = it })
            if (showValues) {
                ChoiceRow(
                    label = "Value placement",
                    options = RadarValuePlacement.entries.toList(),
                    selected = valuePlacement,
                    labelOf = { placement ->
                        when (placement) {
                            RadarValuePlacement.DATA_POINT -> "On data points"
                            RadarValuePlacement.BELOW_AXIS_LABEL -> "Under labels"
                        }
                    },
                    onSelect = { valuePlacement = it },
                )
            }
            ControlSection(title = "Outline")
            IntSliderRow(label = "Line width", value = lineWidth, valueRange = 1..8, onValueChange = { lineWidth = it })
            IntSliderRow(
                label = "Point radius",
                value = pointRadius,
                valueRange = 0..12,
                onValueChange = { pointRadius = it },
            )
            SwitchRow(label = "Inner circle on points", checked = innerCircle, onCheckedChange = { innerCircle = it })
            SwitchRow(label = "Stagger the entry", checked = stagger, onCheckedChange = { stagger = it })
            ControlSection(title = "Data")
            PlaygroundActionRow(primaryLabel = "Shuffle data", onPrimary = { tick++ })
            ControlSection(title = "Last click")
            Text(text = clicked ?: "Tap a profile", style = MaterialTheme.typography.bodySmall)
        },
    )
}

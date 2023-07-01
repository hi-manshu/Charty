## CurveLineChart

The `CurveLineChart` is a composable function that displays a line chart with curved lines using the
Jetpack Compose framework. It is used to visualize data in a smooth curve format.

### Function Signature

```kotlin

@Composable
fun CurveLineChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    radiusScale: Float = 0.02f,
    lineConfig: LineConfig = LineChartDefaults.defaultConfig(),
    chartColors: CurvedLineChartColors = CurvedLineChartDefaults.defaultColor(),
) {
    //Implementation
}
```

### Parameters

- `dataCollection`: This parameter is of type `ChartDataCollection` and represents the data that
  will be plotted on the chart. It contains a collection of `LineData`.
- `modifier`: This parameter is of type `Modifier` and is used to modify the appearance or behavior
  of the chart.
- `padding`: This parameter is of type `Dp` and specifies the padding around the chart.
- `axisConfig`: This parameter is of type `AxisConfig` and is used to configure the appearance and
  behavior of the chart axes.
    - Where, `AxisConfig` looks like,

```kotlin
data class AxisConfig(
    val showAxes: Boolean,
    val showGridLines: Boolean,
    val showGridLabel: Boolean,
    val axisStroke: Float,
    val minLabelCount: Int,
    val axisColor: Color,
    val gridColor: Color,
)
```

- `radiusScale`: This parameter is of type `Float` and determines the scale of the curve's radius.
  It affects the curvature of the lines in the chart.
- `lineConfig`: This parameter is of type `LineConfig` and allows customization of the line
  appearance.
    - where, `LineConfig` looks like,

```kotlin
data class LineConfig(
    val hasSmoothCurve: Boolean,
    val hasDotMarker: Boolean,
    val strokeSize: Float
)
```

- `chartColors`: This parameter is of type `CurvedLineChartColors` and It allows customization of
  colors.
    - where, `CurvedLineChartColors` looks like,

```kotlin
data class CurvedLineChartColors(
    val dotColor: List<Color> = emptyList(),
    val backgroundColors: List<Color> = emptyList(),
    val contentColor: List<Color> = emptyList(),
)
```

#### Copyright (c) 2023. Charty Contributor
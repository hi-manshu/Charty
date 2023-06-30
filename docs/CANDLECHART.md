### CandleChartChart

To use the CandleChartChart, follow the steps below:

- Include the Charty library in your Android project.
- Use the `CandleChartChart` composable in your code:

```kotlin @Composable
fun CandleChartChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    chartColors: CurvedLineChartColors = CurvedLineChartDefaults.defaultColor(),
) {
    // Implementation details...
}
```

### Parameters

`CandleChartChart` accepts the following parameters:

- `dataCollection`: A `ChartDataCollection` object representing the data to be displayed in bubble
  chart of type `BubbleData`.
- `modifier`: Optional `Modifier` to customize the appearance and behavior of the chart.
- `padding`: Optional `Dp` value representing the padding around the chart. Default is `16.dp`.
- `chartColors`: Optional `CurvedLineChartColors` value representing the color used in the chart.
  where it looks like,

```kotlin
data class CurvedLineChartColors(
    val dotColor: List<Color> = emptyList(),
    val backgroundColors: List<Color> = emptyList(),
    val contentColor: List<Color> = emptyList(),
)
```

- `axisConfig`: Optional `AxisConfig` object representing the configuration of the chart axes.
  Default is `ChartDefaults.axisConfigDefaults()`.

Where, AxisConfig looks like,

```kotlin
data class AxisConfig(
    val showAxes: Boolean,
    val showGridLines: Boolean,
    val showGridLabel: Boolean,
    val axisStroke: Float,
    val minLabelCount: Int,
    val axisColor: Color,
    val gridColor: Color = axisColor.copy(alpha = 0.5F),
)
```

#### Copyright (c) 2023. Charty Contributor
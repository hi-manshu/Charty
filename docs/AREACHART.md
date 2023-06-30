### AreaChart

To use the AreaChart, follow the steps below:

- Include the Charty library in your Android project.
- Use the `AreaChart` composable in your code:

```kotlin @Composable
fun AreaChart(
    areaData: ComposeList<AreaData>,
    modifier: Modifier = Modifier,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    padding: Dp = 16.dp,
)  {
    // Implementation details...
}
```

### Parameters

`AreaChart` accepts the following parameters:

- `areaData`: A `ComposeList` object representing the data to be displayed in area
  chart.

- `modifier`: Optional `Modifier` to customize the appearance and behavior of the chart.
- `padding`: Optional `Dp` value representing the padding around the chart. Default is `16.dp`.
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
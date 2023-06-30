### BarChart

To use the BarChart composable, follow the steps below:

- Include the Charty library in your Android project.
- Use the `BarChart` composable in your code:

```kotlin @Composable
fun BarChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    barSpacing: Dp = 8.dp,
    padding: Dp = 16.dp,
    barColor: Color = Color.Blue,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
) {
    // Implementation details...
}
```

or

```kotlin @Composable
fun BarChart(  
  dataCollection: ChartDataCollection,  
  modifier: Modifier = Modifier,  
  barSpacing: Dp = 8.dp,  
  padding: Dp = 16.dp,  
  barColor: Color = Color.Blue,  
  axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),  
){
    // Implementation details...
}
```

In the above `BarChart`, we have `barColor` that will override the individual BarData's `color`

### Parameters

The `BarChart` composable accepts the following parameters:

- `dataCollection`: A `ChartDataCollection` object representing the data to be displayed in the bar
  chart.

- `modifier`: Optional `Modifier` to customize the appearance and behavior of the chart.

- `barSpacing`: Optional `Dp` value representing the spacing between bars in the chart. Default
  is `8.dp`.

- `padding`: Optional `Dp` value representing the padding around the chart. Default is `16.dp`.

- `barColor`: Optional `Color` value representing the color of the bars in the chart. Default
  is `Color.Blue`.
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
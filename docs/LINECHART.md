## LineChart Documentation

The `LineChart` is a composable function that displays a line chart using the Jetpack Compose
framework. It is used to visualize data as a series of connected points with straight lines.

### Function Signature

```kotlin

@Composable
fun LineChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    radiusScale: Float = 0.02f,
    lineConfig: LineConfig = LineChartDefaults.defaultConfig(),
    chartColors: LineChartColors = LineChartDefaults.defaultColor(),
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
- `radiusScale`: This parameter is of type `Float` and determines the scale of the dot's radius. It
  affects the size of the data points in the chart.
- `lineConfig`: This parameter is of type `LineConfig` and allows customization of the line
  appearance.
- `chartColors`: This parameter is of type `LineChartColors` and provides a set of default colors
  for the chart components.

## LineChart (Alternate Signature)

```kotlin

@Composable
fun LineChart(
    dataCollection: ChartDataCollection,
    dotColor: Color,
    lineColor: Color,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    padding: Dp = 16.dp,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    lineConfig: LineConfig = LineChartDefaults.defaultConfig(),
    radiusScale: Float = 0.02f,
) {
  //Implementation
}
```

### Additional Parameters
- `dotColor`: This parameter is of type `Color` and represents the color of the data points (dots) on the chart.
- `lineColor`: This parameter is of type `Color` and represents the color of the lines connecting the data points.


#### Copyright (c) 2023. Charty Contributor
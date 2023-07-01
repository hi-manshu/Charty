### CircleChart

To use the CircleChart, follow the steps below:

- Include the Charty library in your Android project.
- Use the `CircleChart` composable in your code:

```kotlin @Composable
fun CircleChart(
     dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    canAnimate: Boolean = true,
    textLabelTextConfig: CircleChartLabelTextConfig = CircleConfigDefaults.defaultTextLabelConfig(),
    config: CircleChartConfig = CircleConfigDefaults.circleConfigDefaults(),
) {
    // Implementation details...
}
```

### Parameters

`CircleChart` accepts the following parameters:

- `dataCollection`: A `ChartDataCollection` object representing the data to be displayed in CircleChart
  of type `CircleData`.
- `modifier`: Optional `Modifier` to customize the appearance and behavior of the chart.
- `canAnimate`: Optional `Boolean` value representing if the chart should animate. Default is `true`.
- `textLabelTextConfig` : Optional `CircleChartLabelTextConfig` type parameter. Configuration for the text label that appears in the center of the chart. Default configuration can be accessed through `CircleConfigDefaults.defaultTextLabelConfig()`.
where, it looks like,
```kotlin
data class CircleChartLabelTextConfig(
  val textSize: TextUnit,
  val fontStyle: FontStyle? = null,
  val fontWeight: FontWeight? = null,
  val fontFamily: FontFamily? = null,
  val indicatorSize: Dp = 10.dp,
  val maxLine: Int = 1,
  val overflow: TextOverflow = TextOverflow.Ellipsis
)
```
- `config`: Optional `CircleChartConfig` type parameter. Configuration for the circle chart appearance. Default configuration can be accessed through CircleConfigDefaults.circleConfigDefaults().
where, it looks like,
```kotlin
data class CircleChartConfig(
  val startAngle: StartAngle = StartAngle.Zero,
  val maxValue: Float?,
  val showLabel: Boolean
)
```

#### Copyright (c) 2023. Charty Contributor
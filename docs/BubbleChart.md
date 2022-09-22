## Bubble Chart

> A bubble chart is a type of chart that displays three dimensions of data.

### Using BubbleChart in your project:

1. When you want a gradient shade

```kotlin
  BubbleChart(
    modifier = Modifier,
    colors = // colors,
    bubbleData = // list of BubbleData
 )
```

2. When you want a solid shade:

```kotlin
  BubbleChart(
    modifier = Modifier,
    color = // color,
    bubbleData = // list of BubbleData
 )
```

### Creating Data Set:

to create a data set you need to pass List of `BubbleData`, where `BubbleData` looks like:

```kotlin
data class BubbleData(
    val xValue: Any,
    val yValue: Float,
    val volumeSize: Float,
)
```
Here, `xValue` will be used as label, `yValue` will act as progress and `volumeSize` is parameter which scales the circle.

### Additional Configuration (Optional)
- To edit Config of the Axis, to suit your need to use `AxisConfig`
- To add padding the the chart, you can also use `ChartDimens`
- To edit Config of the Axis, to suit your need to use `AxisConfig`

## HorizontalBarChart

> A bar chart or bar graph is a chart or graph that presents categorical data with rectangular bars with heights or lengths proportional to the values that they represent

### Using HorizontalBarChart in your project:

1. When you want a gradient shade

```kotlin
  HorizontalBarChart(
    modifier = Modifier,
    onBarClick = { // handle Click for individual bar}
    colors = // colors
    horizontalBarData = // list of HorizontalBarData
        )
```

2. When you want a solid shade:

```kotlin
  HorizontalBarChart(
    modifier = Modifier,
    onBarClick = { // handle Click for individual bar},
    color = // colors
    horizontalBarData = // list of HorizontalBarData
        )
```

### Creating Data Set:

to create a data set you need to pass List of `HorizontalBarData`, where `HorizontalBarData` looks like:
```kotlin
data class HorizontalBarData(val xValue: Float, val yValue: Any)
```
Here, `yValue` will be used as Labels and `xValue` will represent the bars.

### Additional Configuration (Optional)
- To add padding the the chart, you can also use `ChartDimens`
- To edit Config of the Axis, to suit your need to use `AxisConfig`
- To edit Individual Bar config of it having corner radius you need to use `BarConfig`

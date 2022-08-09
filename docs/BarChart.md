## BarChart

> A bar chart or bar graph is a chart or graph that presents categorical data with rectangular bars with heights or lengths proportional to the values that they represent

### Using BarChart in your project:

1. When you want a gradient shade

```kotlin
  BarChart(
    modifier = Modifier,
    onBarClick = { // handle Click for individual bar}
    colors = // colors
    barData = // list of BarData
        )
```

2. When you want a solid shade:

```kotlin
  BarChart(
    modifier = Modifier,
    onBarClick = { // handle Click for individual bar},
    color = // colors
    barData = // list of BarData
        )
```

### Creating Data Set:

to create a data set you need to pass List of `BarData`, where `BarData` looks like:
```kotlin
data class BarData(val xValue: Any, val yValue: Float)
```
Here, `xValue` will be used as Labels and `yValue` will represent the bars.

### Additional Configuration (Optional)
- To add padding the the chart, you can also use `ChartDimens`
- To edit Config of the Axis, to suit your need to use `AxisConfig`
- To edit Individual Bar config of it having corner radius you need to use `BarConfig`

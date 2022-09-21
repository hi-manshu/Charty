## BarChart

> The stacked bar chart (aka stacked bar graph) extends the standard bar chart from looking at numeric values across one categorical variable to two.

### Using StackedBarChart in your project:

```kotlin
  StackedBarChart(
    modifier = Modifier,
    onBarClick = { // handle Click for individual bar}
    colors = // colors,
    stackBarData = // list of stackBarData
    )
```

### Creating Data Set:

to create a data set you need to pass List of `StackedBarData`, where `StackedBarData` looks like:
```kotlin
data class StackedBarData(val xValue: Any, val yValue: List<Float>)
```
Here, `xValue` will be used as Labels and `yValue` will represent the bars.

Also, count of `yValue` and count of Colors should be same or it will throw an exception.

### Additional Configuration (Optional)
- To add padding the the chart, you can also use `ChartDimens`
- To edit Config of the Axis, to suit your need to use `AxisConfig`
- To edit Individual Bar config of it having corner radius you need to use `BarConfig`

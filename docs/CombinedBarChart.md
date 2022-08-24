## BarChart

> The combination chart is a visualization that combines the features of the bar chart and the line chart. The combination chart displays the data using a number of bars and/or lines, each of which represent a particular category.

### Using CombinedBarChart in your project:

1. When you want a gradient shade on bar and line

```kotlin
  CombinedBarChart(
    modifier = Modifier,
    onClick = { },// returns CombinedBarData}
    barColors = // list of colors,
    lineColors = // list of colors,
    combinedBarData = // list of combinedBarData
   )
```

2. When you want a solid shade bar and line:

```kotlin
  CombinedBarChart(
    modifier = Modifier,
    onClick = { },// returns CombinedBarData}
    barColor = // color,
    lineColor = // color,
    combinedBarData = // list of combinedBarData
)
```
3. When you want anyone of them gradient, you can directly pass a list of colors to either `barColors` or `lineColors`.
4. 
### Creating Data Set:

to create a data set you need to pass List of `CombinedBarData`, where `CombinedBarData` looks like:
```kotlin
data class CombinedBarData(val xValue: Any, val yBarValue: Float, val yLineValue: Float)
```
Here, `xValue` will be used as Labels and `yBarValue` will represent the bars and `yLineValue` represents the value for Line.

### Additional Configuration (Optional)
- To add padding the the chart, you can also use `ChartDimens`
- To edit Config of the Axis, to suit your need to use `AxisConfig`
- To edit bar config of it having rounded corners, dot marker etc you need to use `CombinedBarConfig`

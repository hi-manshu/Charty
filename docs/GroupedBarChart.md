## GroupedBarChart

> grouped bar charts are Bar charts in which multiple sets of data items are compared, with a single color used to denote a specific series across all sets.

### Using GroupedBarChart in your project:

1. When you want a gradient shade

```kotlin
  GroupedBarChart(
    modifier = Modifier,
    onBarClick = { // handle Click for individual bar},
    colors = // colors
    groupedBarData = // list of GroupedBarData
    )
```

### Creating Data Set:

to create a data set you need to pass List of `GroupedBarData`, where `GroupedBarData` looks like:
```kotlin
data class GroupedBarData(val barData: List<BarData>, val colors: List<Color>)
```
Here, `barData` will be used as Creating group of bar and `colors` will represent the colors of the individual bar.

### Additional Configuration (Optional)
- To add padding the the chart, you can also use `ChartDimens`
- To edit Config of the Axis, to suit your need to use `AxisConfig`
- To edit Individual Bar config of it having corner radius you need to use `BarConfig`

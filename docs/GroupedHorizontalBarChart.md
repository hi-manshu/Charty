## GroupedHorizontalBarChart

> A bar chart or bar graph is a chart or graph that presents categorical data with rectangular bars with width proportional relative to each other to the values that they represent

### Using GroupedHorizontalBarChart in your project:

1. When you want a gradient shade

```kotlin
  GroupedHorizontalBarChart(
    modifier = Modifier,
    onBarClick = { // handle Click for HorizontalBarData}
    colors = // color 
    groupedBarData = // list of GroupedHorizontalBarData,
 )
```

### Creating Data Set:

to create a data set you need to pass List of `GroupedHorizontalBarChart`, where `GroupedHorizontalBarChart` looks like:
```kotlin
data class GroupedHorizontalBarData(
    val horizontalBarData: List<HorizontalBarData>,
    val colors: List<Color> = List(horizontalBarData.count()) { Color.Transparent }
)
```
Here, `horizontalBarData` will be used as Creating group of bar and `colors` will represent the colors of the individual bar.

### Additional Configuration (Optional)
- To add padding the the chart, you can also use `ChartDimens`
- To edit Config of the Axis, to suit your need to use `HorizontalAxisConfig`
- To edit Individual Bar config of it having labels use `HorizontalBarConfig`

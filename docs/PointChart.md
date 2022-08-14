## PointChart

> A bar chart or bar graph is a chart or graph that presents categorical data with rectangular bars with heights or lengths proportional to the values that they represent

### Using PointChart in your project:

1. When you want a gradient shade

```kotlin
  PointChart(
    modifier = Modifier,
    colors = // colors
    pointData = // list of PointData 
  )
```

2. When you want a solid shade:

```kotlin
  PointChart(
    modifier = Modifier,
    color = // colors
    pointData = // list of PointData 
  )
```

### Creating Data Set:

to create a data set you need to pass List of `PointData`, where `PointData` looks like:
```kotlin
data class PointData(val xValue: Any, val yValue: Float)
```
Here, `xValue` will be used as Labels and `yValue` will represent the progress.

### Additional Configuration (Optional)
- To add padding the the chart, you can also use `ChartDimens`
- To edit Config of the Axis, to suit your need to use `AxisConfig`
- To edit the point representation use `PointConfig`

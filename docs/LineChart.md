## LineChart

> A line chart chart or graph that presents categorical data with line progress views with heights proportional to the values that they represent

### Using LineChart in your project:

1. When you want a gradient shade

```kotlin
  LineChart(
    modifier = Modifier,
    colors = // colors
    lineData = // list of LineData 
  )
```

2. When you want a solid shade:

```kotlin
  LineChart(
    modifier = Modifier,
    color = // colors
    lineData = // list of LineData 
  )
```

### Creating Data Set:

to create a data set you need to pass List of `LineData`, where `LineData` looks like:
```kotlin
data class LineData(val xValue: Any, val yValue: Float)
```
Here, `xValue` will be used as Labels and `yValue` will represent the progress.

### Additional Configuration (Optional)
- To add padding the the chart, you can also use `ChartDimens`
- To edit Config of the Axis, to suit your need to use `AxisConfig`
- To edit the line representation like `smooth curve` or having additional view of `dot market`  use `LineConfig`

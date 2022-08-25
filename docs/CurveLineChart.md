## CurveLineChart

> A curve line chart chart or graph that presents categorical data with line progress views with curved lines with heights proportional to the values that they represent

### Using CurveLineChart in your project:

1. When you want a gradient shade in both line and chart

```kotlin
  CurveLineChart(
    modifier = Modifier,
    chartColors = // colors
    lineColors = // colors
    lineData = // list of LineData 
  )
```

2. When you want a solid shade in both line and chart

```kotlin
  CurveLineChart(
    modifier = Modifier,
    chartColor = // colors
    lineColor = // colors
    lineData = // list of LineData 
  )
```

3. When you want a solid shade in any one of line and chart then pass list of colors in one and one in another,

```kotlin
  CurveLineChart(
    modifier = Modifier,
    chartColor = // colors
    lineColor = // colors
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

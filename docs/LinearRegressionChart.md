## LinearRegressionChart

### Using LinearRegressionChart in your project:

1. When you want a gradient shade:

```kotlin
LinearRegressionChart(
    modifier = Modifier,
    scatterColors = /* A list of colors to apply to scatter data */
    lineColors = /* A list of colors to apply to the regression line */
    linearRegressionData = /* A list of LinearRegressionData */
)
```

2. When you want a solid shade:

```kotlin
LinearRegressionChart(
    modifier = Modifier,
    scatterColor = /* The color to apply to scatter data */
    lineColor = /* The color to apply to the regression line */
    linearRegressionData = /* A list of LinearRegressionData */
)
```

### Creating a data set

To create a data set, you need to pass a list of `LinearRegressionData`, where `LinearRegressionData` looks like:
```kotlin
data class LinearRegressionData(val xValue: Float, val yValue: Float)
```
Here, `xValue` is the independent variable and `yValue` is the dependent variable.
The axes of the graph are scaled continuously based on the min and max values of each axis.

### Additional Configuration (Optional)
- To add padding to the chart, you can also use `ChartDimens`
- To edit the axis configurations, use `AxisConfig`
- To edit the style and size of the scatter points and regression line use `LinearRegressionConfig`
- To edit the y-axis labels and guidelines, use `YLabels`
- To edit the x-axis labels and guidelines, use `XLabels`

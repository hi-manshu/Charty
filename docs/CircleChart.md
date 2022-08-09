## CircleChart

> A circle chart or bar graph is a chart or graph that presents categorical data with circular progress

### Using CircleChart in your project:

1. When you want a gradient shade

```kotlin
  CircleChart(
    modifier = Modifier,
    isAnimated = true/false,
    colors = // colors,
    circleData = // list of CircleData
    )
```

2. When you want a solid shade:
```kotlin
  CircleChart(
    modifier = Modifier,
    isAnimated = true/false,
    color = // color
    circleData = // list of CircleData
    )
```

### Creating Data Set:

to create a data set you need to pass List of `CircleData`, where `CircleData` looks like:
```kotlin
data class CircleData(val xValue: Any, val yValue: Float, val color: Color? = null)
```
Here, `xValue` will be used as label, `yValue` will act as progress and `color` is optional parameter but if provided that will represent the individual color of the progress

### Additional Configuration (Optional)
- To change the start position of the Circle, you can use `StartPosition` with either of provided values or your custom angle.
- You can also provide a `maxValue` to wrap the max progress of the chart. 

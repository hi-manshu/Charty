## PointChart Documentation

To use the PointChart, follow the steps below:

- Include the Charty library in your Android project.
- Use the `PointChart` composable in your code:
-

### Function Signature

```kotlin

@Composable
fun PointChart(
    pointData: ChartDataCollection,
    contentColor: Color,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    padding: Dp = 16.dp,
    pointType: PointType = PointType.Stroke(),
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    radiusScale: Float = 0.02f,
)
```

### Additional Function Signature

```kotlin

@Composable
fun PointChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    pointType: PointType = PointType.Stroke(),
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    radiusScale: Float = 0.02f,
    chartColors: ChartColors = ChartDefaults.colorDefaults(),
)
```

### Parameters

- `dataCollection`: This parameter is of type `ChartDataCollection` and represents the data that
  will be plotted on the chart. It contains a collection of `PointData`.
- `modifier`: This parameter is of type `Modifier` and is used to modify the appearance or behavior
  of the chart.
- `backgroundColor`: This parameter is of type `Color` and represents the background color of the
  chart. The default value is `Color.White`.
- `padding`: This parameter is of type `Dp` and represents the padding around the chart. The default
  value is `16.dp`.
- `pointType`: This parameter is of type `PointType` and represents the type of point rendering on
  the chart. It can be either `PointType.Stroke()` (hollow points) or `PointType.Fill()` (filled
  points). The default value is `PointType.Stroke()`.
- `axisConfig`: This parameter is of type `AxisConfig` and allows customization of the axis
  appearance and labels on the chart. The default value is `ChartDefaults.axisConfigDefaults()`.
- `radiusScale`: This parameter is of type `Float` and represents the scale factor for the radius of
  the data points on the chart. The default value is `0.02f`.
- `contentColor`: This parameter is of type `Color`.
- `chartColors`: This parameter is of type `ChartColors` and allows customization of the chart's colors. The default value is `ChartDefaults.colorDefaults()`.

#### Copyright (c) 2023. Charty Contributor
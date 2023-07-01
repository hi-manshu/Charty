## GroupedBarChart

To use the GroupedBarChart, follow the steps below:

- Include the Charty library in your Android project.
- Use the `GroupedBarChart` composable in your code:

### Function Signature

```kotlin
@Composable
fun GroupedBarChart(
    groupBarDataCollection: ComposeList<GroupBarData>,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    barWidthRatio: Float = 0.8f,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    textLabelTextConfig: ChartyLabelTextConfig = ChartDefaults.defaultTextLabelConfig(),
) {
    // Function body
}
```


### Parameters
- `groupBarDataCollection`: `ComposeList<GroupBarData>`
  - A collection of `GroupBarData` objects representing the data for each group in the chart.
- `modifier`: `Modifier` (optional)
  - Modifier to be applied to the chart layout.
- `padding`: `Dp` (optional)
  - The padding around the chart. Default is `16.dp`.
- `barWidthRatio`: `Float` (optional)
  - The ratio of the width of each bar to the total width available for each group. Must be within the range of 0.4f to 0.9f. The default value is `0.8f`, which provides a visually appealing view.
- `axisConfig`: `AxisConfig` (optional)
  - Configuration for the chart's axis appearance. Default configuration can be accessed through `ChartDefaults.axisConfigDefaults()`.
  - Properties of `AxisConfig`:
    - `showAxes`: `Boolean` - Specifies whether to show the X and Y axes. Default is `true`.
    - `axisColor`: `Color` - Color of the axes. Default is `Color.Black`.
    - `axisStroke`: `Stroke` - Stroke configuration for the axes. Default is `Stroke(width = 2.dp.toPx())`.
    - `showGridLines`: `Boolean` - Specifies whether to show the grid lines. Default is `true`.
    - `showGridLabel`: `Boolean` - Specifies whether to show labels for the grid lines. Default is `true`.
- `textLabelTextConfig`: `ChartyLabelTextConfig` (optional)
  - Configuration for the chart's label text appearance. Default configuration can be accessed through `ChartDefaults.defaultTextLabelConfig()`.
  - Properties of `ChartyLabelTextConfig`:
    - `textColor`: `Color` - Color of the label text. Default is `Color.Black`.
    - `textSize`: `TextUnit` - Size of the label text. Default is `12.sp`.
    - `fontStyle`: `FontStyle` - Style of the label text. Default is `FontStyle.Normal`.
    - `textAlignment`: `TextAlign` - Alignment of the label text. Default is `TextAlign.Center`.


#### Copyright (c) 2023. Charty Contributor
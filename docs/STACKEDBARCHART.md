### StackedBarChart 

To use the StackedBarChart, follow the steps below:

- Include the Charty library in your Android project.
- Use the `StackedBarChart` composable in your code:

### Function Signature

```kotlin

@Composable
fun StackedBarChart(
    stackBarData: ComposeList<StackBarData>,
    modifier: Modifier = Modifier,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    padding: Dp = 16.dp,
    spacing: Dp = 4.dp,
    textLabelTextConfig: ChartyLabelTextConfig = ChartDefaults.defaultTextLabelConfig(),
)
```


### Parameters
- `stackBarData`: This parameter is of type `ComposeList<StackBarData>` and represents the data that will be plotted on the stacked bar chart. It contains a list of `StackBarData` objects.
- `modifier`: This parameter is of type `Modifier`.
- `axisConfig`: This parameter is of type `AxisConfig` and allows customization of the axis appearance and labels on the chart. It provides options to configure the axis color, stroke width, and label count, among others. The default value is `ChartDefaults.axisConfigDefaults()`.
- `padding`: This parameter is of type `Dp` and represents the padding around the chart. The default value is `16.dp`.
- `spacing`: This parameter is of type `Dp` and represents the spacing between the stacked bars. The default value is `4.dp`.
- `textLabelTextConfig`: This parameter is of type `ChartyLabelTextConfig` and allows customization of the text labels on the chart. The default value is `ChartDefaults.defaultTextLabelConfig()`.

#### Copyright (c) 2023. Charty Contributor
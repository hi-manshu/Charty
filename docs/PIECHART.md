### PieChart 

To use the PieChart, follow the steps below:

- Include the Charty library in your Android project.
- Use the `PieChart` composable in your code:

```kotlin

@Composable
fun PieChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    textLabelTextConfig: ChartyLabelTextConfig = ChartDefaults.defaultTextLabelConfig(),
    pieChartConfig: PieChartConfig = PieChartDefaults.defaultConfig(),
)
```


### Parameters
- `dataCollection`: This parameter is of type `ChartDataCollection` and represents the data that will be plotted on the chart. It contains a collection of `PieData`.
- `modifier`: This parameter is of type `Modifier` and is used to modify the appearance or behavior of the chart.
- `textLabelTextConfig`: This parameter is of type `ChartyLabelTextConfig` and allows customization of the labels displayed inside the chart. It provides options to configure the text appearance, such as color, size, and style.
- `pieChartConfig`: This parameter is of type `PieChartConfig` and allows customization of the pie chart appearance and behavior.It looks like,
```kotlin
data class PieChartConfig(
    val donut: Boolean,
    val showLabel: Boolean,
    val startAngle: StartAngle = StartAngle.Zero
)
```

#### Copyright (c) 2023. Charty Contributor
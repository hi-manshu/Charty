### CandleStickChart

To use the CandleStickChart, follow the steps below:

- Include the Charty library in your Android project.
- Use the `CandleStickChart` composable in your code:

```kotlin @Composable
fun CandleStickChart(
    candleData: ComposeList<CandleData>,
    modifier: Modifier = Modifier,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    padding: Dp = 16.dp,
    candleConfig: CandleStickConfig = CandleStickDefaults.defaultCandleStickConfig(),
) {
    // Implementation details...
}
```

### Parameters

`CandleStickChart` accepts the following parameters:

- `candleData`: A `ComposeList` object representing the data to be displayed in CandleStickChart
  of type `CandleData`.
- `modifier`: Optional `Modifier` to customize the appearance and behavior of the chart.
- `padding`: Optional `Dp` value representing the padding around the chart. Default is `16.dp`.
- `candleConfig`: Optional `CandleStickConfig` value representing the config used in the chart.
  where it looks like,

```kotlin
@Immutable
data class CandleStickConfig(
  val positiveColor: Color,
  val negativeColor: Color,
  val wickColor: Color,
  val canCandleScale: Boolean,
  val wickWidthScale: Float = 0.05f,
)
```

- `axisConfig`: Optional `AxisConfig` object representing the configuration of the chart axes.
  Default is `ChartDefaults.axisConfigDefaults()`.

Where, AxisConfig looks like,

```kotlin
data class AxisConfig(
    val showAxes: Boolean,
    val showGridLines: Boolean,
    val showGridLabel: Boolean,
    val axisStroke: Float,
    val minLabelCount: Int,
    val axisColor: Color,
    val gridColor: Color = axisColor.copy(alpha = 0.5F),
)
```

#### Copyright (c) 2023. Charty Contributor
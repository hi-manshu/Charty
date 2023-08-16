### GaugeChart

To use the GaugeChart, follow the steps below:

- Include the Charty library in your Android project.
- Use the `GaugeChart` composable in your code:

```kotlin 
@Composable
fun GaugeChart(
    percentValue: Int,
    modifier: Modifier = Modifier,
    gaugeChartConfig: GaugeChartConfig = GaugeChartDefaults.gaugeConfigDefaults(),
    needleConfig: NeedleConfig = GaugeChartDefaults.needleConfigDefaults(),
    animated: Boolean = true,
    animationSpec: AnimationSpec<Float> = tween(),
) {
    // Implementation details...
}
```

### Parameters
- `percentValue`: `Int`
  - The percentage value to be displayed on the gauge chart. Must be within the range of 1 to 100.
- `modifier`: `Modifier` (optional)
  - Modifier to be applied to the chart layout.
- `gaugeChartConfig`: `GaugeChartConfig` (optional)
  - Configuration for the gauge chart appearance. Default configuration can be accessed through `GaugeChartDefaults.gaugeConfigDefaults()`.
- Properties of `GaugeChartConfig`:
  - `placeHolderColor`: `Color` - Color of the background arc of the chart. Default is `Color.LightGray`.
  - `primaryColor`: `Color` - Color of the primary arc indicating the current value. Default is `Color.Blue`.
  - `textColor`: `Color` - Color of the primary text indicating the current value.
  - `showNeedle`: `Boolean` - Specifies whether to show the needle indicating the current value. Default is `true`.
  - `showText`: `Boolean` - Specifies whether to show the text of the current value. Default is `true`.
  - `showIndicator`: `Boolean` - Specifies whether to show the minute hour dividers. Default is `true`.
  - `indicatorColor`: `Color` - Color of the minute hour dividers. Default is `Color.Red`.
  - `indicatorWidth`: `Dp` - Width of the minute hour dividers. Default is `2.dp`.
  - `strokeWidth`: `Dp` - Width of the chart arcs. Default is `16.dp`.
- `needleConfig`: `NeedleConfig` (optional)
  - Configuration for the needle appearance. Default configuration can be accessed through `GaugeChartDefaults.needleConfigDefaults()`.
  - Properties of `NeedleConfig`:
    - `color`: `Color` - Color of the needle. Default is `Color.Black`.
    - `strokeWidth`: `Dp` - Width of the needle stroke. Default is `4.dp`.
- `animated`: `Boolean` (optional)
  - Specifies whether the chart should animate when data changes. Default is `true`.
- `animationSpec`: `AnimationSpec<Float>` (optional)
  - Animation specification for the chart animation. Default is `tween()`.


#### Copyright (c) 2023. Charty Contributor
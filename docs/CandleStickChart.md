## CandleStickChart

> A candlestick chart is a style of financial chart used to describe price movements of a security, derivative, or currency.

### Using CandleStickChart in your project:

```kotlin
  CandleStickChart(
    candleEntryData = // list of CandleEntry,
    modifier = Modifier,
  )
```

### Creating Data Set:

to create a data set you need to pass List of `CandleEntry`, where `CandleEntry` looks like:
```kotlin
data class CandleEntry(
    val high: Float,
    val low: Float,
    val opening: Float,
    val closing: Float
)
```

### Additional Configuration (Optional)
- To edit Config of the Axis, to suit your need to use `AxisConfig`
- To edit Individual Bar config of it having corner radius you need to use `BarConfig`

### BarChart

To implement the bar chart, we can call the Composable,

1. When you want a gradient shade
```kotlin
  BarChart(
    modifier = Modifier,
    onBarClick = { // handle Click for individual bar},
    colors = // colors
    barData = // list of BarData
)
```

2. When you want a solid shade: 
```kotlin
  BarChart(
    modifier = Modifier,
    onBarClick = { // handle Click for individual bar},
    color = // colors
    barData = // list of BarData
)
```

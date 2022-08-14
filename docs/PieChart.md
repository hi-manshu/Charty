## PieChart

### Using PieChart in your project:
> A PieChart is a graph in which a circle is divided into sectors that each represent a proportion of the whole.

1. When you want a gradient shade

```kotlin
  PieChart(
    modifier = Modifier,
    isDonut = true/false,
    onSectionClicked = {},
    valueTextColor = //Color,
    colors = // colors
    data = // list of Floats ,
  )
```

### Creating Data Set:

to create a data set you need to pass List of `Float`, where it will draw the value in proportion.

### Additional Configuration (Optional)
- To add your color set  use `PieConfig` to pass on your set of color, make sure you use colors of total size of data.
- To make the chart looks a donut make `isDonut` as `true`


### Mention:
- Muñoz : For base work inspiration for PieChart library

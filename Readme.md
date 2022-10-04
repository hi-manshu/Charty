## Charty : Elementary Chart library for Compose

![Charty](img/charty-banner.png)

Chart Library built using Jetpack Compose and is highly customizable. Updates coming soon!
_Made with ❤️ for Android Developers by Himanshu_

[![Github Followers](https://img.shields.io/github/followers/hi-manshu?label=Follow&style=social)](https://github.com/hi-manshu)
[![Twitter Follow](https://img.shields.io/twitter/follow/hi_man_shoe?label=Follow&style=social)](https://twitter.com/hi_man_shoe)
[![Twitter Follow](https://img.shields.io/badge/Featured%20in%20androidweekly.net-%23532-orange)](https://androidweekly.net/issues/issue-532)
[![Sample App](https://img.shields.io/github/v/release/hi-manshu/Charty?color=7885FF&label=Sample%20App&logo=android&style=for-the-badge)](https://github.com/hi-manshu/Charty/releases/latest/download/charty-sample.apk)

## Implementation

### Gradle setup

In `build.gradle` of app module, include this dependency

```gradle
dependencies {
  implementation("com.himanshoe:charty:1.1.3-alpha04")
}
```
## Integrating it in your project

1. BarChart

```kotlin
  BarChart(
    modifier = Modifier,
    onBarClick = { // handle Click for individual bar}
    colors = // colors,
    barData = // list of BarData,
    )
```

For more detailed implementation, click [here](docs/BarChart.md)

## Documentation
You can find the detail implementation of the following:

- [CandleStickChart](docs/CandleStickChart.md)
- [BubbleChart](docs/BubbleChart.md)
- [StackedBarChart](docs/StackedBarChart.md)
- [CombinedBarChart](docs/CombinedBarChart.md)
- [HorizontalBarChart](docs/HorizontalBarChart.md)
- [GroupedBarChart](docs/GroupedBarChart.md)
- [GroupedHorizontalBarChart](docs/GroupedHorizontalBarChart.md)
- [CircleChart](docs/CircleChart.md)
- [PointChart](docs/PointChart.md)
- [LineChart](docs/LineChart.md)
- [CurveLineChart](docs/CurveLineChart.md)
- [PieChart](docs/PieChart.md)


### Contribution
Please feel free to fork it and open a PR.

## License

    Copyright 2022 Charty Contributors

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



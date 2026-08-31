# Changelog

Notable changes to Charty. Versions follow [semantic versioning](https://semver.org); breaking
changes are listed first in each release and say what to do about them.

## Unreleased

### Fixed

- **`RadarChartConfig.scaleToFit` fits.** The last of the four dead flags, and the one whose
  absence users could see: labels near the canvas edge simply clipped. Both radar charts now solve
  for the largest radius at which every label — and any value stack beneath it — stays inside the
  canvas, capped at the padding-derived radius and floored at 40% of it. A chart whose labels
  already fit renders byte-for-byte as before, because fitting only ever shrinks; every existing
  doc image is unchanged.
- **`LabelConfig.shouldShowLabelsOutside` places pie labels outside the rim.** Declared and
  documented since it shipped, read by nothing — the playground even offered it as a toggle that did
  nothing. Labels now sit just outside the pie on their slice's angle. Mind your `labelTextStyle`:
  the default is white, chosen for text on coloured slices.
- **`InteractionConfig.enableHoverEffect` exists now.** On hover-capable platforms the slice under
  the pointer grows slightly (less than a selection does, and selection wins). It was another
  documented no-op.

### Changed

- **`RadarCenterConfig.showCenterIcon` and `centerIconSize` are deprecated.** They never drew
  anything and never could: there has never been an icon for the flag to show, nor a way to supply
  one. The real feature arrives instead — `RadarChart` gains a `centerContent` composable slot,
  rendered over the chart's centre above the `centerBackgroundRadius` backdrop, mirroring
  `PieChart`'s slot of the same name.

## 3.2.0

### Added

- **`RadarLabelConfig.valuePlacement`.** `showValues` gained a second placement:
  `RadarValuePlacement.BELOW_AXIS_LABEL` centres each value beneath its axis name, outside the plot,
  where `DATA_POINT` (the default, unchanged) keeps them on the vertices. On-vertex placement
  degrades honestly but unhelpfully when values sit at zero — a zero's vertex is the centre, so every
  zero lands on the same point. Under the labels, each value keeps a stable position whatever the
  data does. With several data sets the values stack beneath the label in data-set order. The gap
  between label and value is `valueGapFraction`, a fraction of the value's own line height, so it
  scales with `valueTextStyle` rather than being a fixed number of pixels. Requested in
  [#179](https://github.com/hi-manshu/charty/issues/179).

## 3.1.1

### Fixed

- **`RadarLabelConfig.showValues` draws the values.** It never had. The property and its companion
  `valueTextStyle` were declared, documented, and read by nothing, so a caller who asked for values
  got labels, no values and no error. Both radar charts now draw each axis's value just outside its
  data point, where the distance is derived from the point radius and the text's own measured size
  rather than a fixed number of pixels — raising the font size keeps the clearance instead of
  overlapping the shape. Reported as [#179](https://github.com/hi-manshu/charty/issues/179).

## 3.1.0

Fixes to the zoom and pan path and to the crosshair label, one new parameter with a default, and two
types moved to a package that all their callers can reach. Nothing is removed: the moved types keep
deprecated aliases at their old names, so code written against 3.0.0 still compiles once recompiled.

### Changed

- **`NegativeValuesDrawMode` and `LineInterpolation` moved to `com.himanshoe.charty.common.config`.**
  Both were sitting in the package of one chart family while four others took them as settings, so a
  line chart's config had to import from `bar.config` to describe its own axis. The old names remain
  as deprecated typealiases, so code written against 3.0.0 keeps compiling; update the import when
  convenient. Recompile rather than swapping the jar — an alias is a source-level compatibility
  measure, not a binary one.
- **Bar data labels are formatted like every other label in the library.** `BarChartConfig`'s default
  formatter ended in a raw `toString()`, which prints a float differently on JS than on the JVM and
  gave values like `12.300000190734863` in a browser. It now rounds to one decimal place as axis
  labels, tooltips and crosshair labels already did. A value carrying more precision than that will
  read shorter than before; pass your own `dataLabelFormatter` to keep the extra digits.

### Fixed

- **`ChartCrosshairConfig.showLabel = false` works on line, area, stacked area, point, bubble, wavy
  and combo charts.** The crosshair's value label moved from the canvas to a Composable overlay and
  the check on this flag did not move with it, so setting it did nothing on those charts while
  continuing to work on the bar family. Charts that never wanted a crosshair label were drawing one.
- **Zoom is reachable without a touchscreen.** Zooming was pinch-only, and a pinch needs two touch
  points, so on desktop and in a browser with a mouse the viewport could never be narrowed. Panning
  is bounded by whatever sits off-screen, which at full width is nothing, so that did nothing either
  — a chart given a `ViewPortState` was inert on exactly the platforms most likely to supply one.
  The wheel and trackpad now zoom, and a horizontal wheel pans.
- **A viewport no longer costs the chart its tooltip.** The zoom gesture claimed every pointer
  change, including a stationary tap, which cancelled the tap detector underneath it. Supplying a
  viewport therefore switched taps off silently: the zoom worked and the tooltip simply stopped
  appearing. The gesture now only consumes an event when it actually moved the viewport.

### Added

- **`rememberViewPortState(initialVisibleFraction)`.** Left at its default a chart shows the whole
  series, and a chart showing everything cannot be panned, so a long series arrives as a row of
  slivers and stays that way until a reader thinks to pinch it. Opening at `0.1f` shows the first
  tenth and is draggable straight away.
  ```kotlin
  val viewport = rememberViewPortState(initialVisibleFraction = 0.1f)
  ```

The zoom and pan items above were reported as [#154](https://github.com/hi-manshu/charty/issues/154).

## 3.0.0

The first stable release of the 3.0 line. From here the public API is committed: nothing outside
`@ChartyExperimental` will break before 4.0.0 without a deprecation cycle first.

The `charty-3d` artifact is the deliberate exception. Every declaration in it carries
`@ChartyExperimental`, which is a `RequiresOptIn` marker at ERROR level — the compiler makes you say
you accept it may move under you. Projected 3D charts are new and their configuration surface is
still being learned from use, so they ship stable-adjacent rather than stable.

### Breaking

Coming from `3.0.0-rc01` — a pre-release, which is what carries no compatibility promise and where
this kind of change belongs. Every one is a **type change on a public property**, so the compiler
will point at each call site. None changes behaviour: the defaults render exactly as before.

- **Chart configs: `tooltipConfig` is now `TooltipConfig?`, defaulting to `null`.** `null` means
  "take the ambient `ChartyTheme`", which is what makes a host app's theme reach the tooltip at all.
  Affects all 19 chart configs.
  ```kotlin
  BarChartConfig(tooltipConfig = TooltipConfig(...))   // still works, still wins over the theme
  BarChartConfig()                                     // now themed instead of hard-coded
  ```
- **`ChartCrosshair.config` is now `ChartCrosshairConfig?`, defaulting to `null`** — same reason.
  `ChartCrosshair()` now takes its styling from the theme.
- **Raw `Color` replaced by `ChartyColor` across the public surface**, so a gradient is sayable
  wherever a solid is: `ChartScaffoldConfig.axisColor`/`gridColor`, `ReferenceLineConfig.color`,
  `TooltipConfig.backgroundColor`/`borderColor`, `RadarChartConfig.centerBackgroundColor`,
  `CircularRingData.shadowColor`, and every role in `ChartyTheme`.
  ```kotlin
  ChartScaffoldConfig(axisColor = Color.Black)                     // before
  ChartScaffoldConfig(axisColor = ChartyColor.Solid(Color.Black))  // after
  ```
- **`ChartLegend(colors:)` takes `List<ChartyColor>`** instead of `List<Color>`, so a legend swatch
  can carry the same gradient its series does.
- **`PointChartConfig.showLabels` removed.** It was read by nothing; the property had no effect.

### Added

- **`charty-3d` 1.0.0** — a separate artifact for projected 3D charts, carrying its own version
  because it arrived here with no releases behind it and because, being experimental, it will move
  faster than `charty` should. It declares the `charty` it was built against, so the pair always
  resolves together. `Bar3DChart` and `Pie3DChart`, both `@ChartyExperimental`. See
  [its page](docs/charty/charts/3d/README.md), which is candid about what depth costs you.
- **`@ChartyExperimental`** — a `RequiresOptIn` marker at ERROR level for APIs that may change in a
  minor release without deprecation.
- **Theming reaches the overlays.** `ChartyTheme` now carries typography (9 roles), component colours
  (26), shapes and dimensions (27), and charts resolve their `null` config slots against it. See
  [Theming](docs/charty/customization/theming.md).
- **Three charts**: `DivergingBarChart`, `GanttChart`, `FunnelChart`.
- **Crosshair** on `SpanChart`, `LollipopBarChart`, `WaterfallChart`, `ComparisonBarChart`.
- **`ChartTooltip` slot** on `BubbleBarChart`, `ComboChart`, `MultilineChart`, `StackedAreaChart`,
  `WavyChart`, `CandlestickChart`, `BubbleChart`.

### Fixed

- **Streaming charts now animate when a point arrives.** `lerpValues` returned the target unchanged
  whenever the list length differed — and appending a point changes the length every tick, so
  nothing tweened at all. An appended point now grows out of its predecessor's value, so the new
  segment extends from where the line left off and anything pinned to the newest point follows
  smoothly instead of jumping.
- **Formatters no longer disagree across platforms.** Ten tooltip and label formatters interpolated
  floats directly, rendering `20` on JS and Wasm but `20.0` on JVM and Android.
- **`BubbleChart` no longer throws on its own defaults** — `pointRadius` (8f) was validated against
  `minBubbleRadius` (10f).
- **`CircularProgressIndicator` no longer animates when rotation is disabled**, which it did by
  default, blocking tests and draining battery.
- **A crosshair no longer swallows taps**; the down event is no longer consumed unconditionally.
- **Tooltips centre on their target** on the calendar heatmap and stacked area, which were half a
  cell out.

### Docs

- Every chart page now opens with an image; 37 generated stills had been referenced by nothing.
- The README chart catalog links to every page, including Sparkline and Angular gauge, which had
  pages but no entry.
- Two `<img>` tags pointing at GIFs that were never produced have been removed.

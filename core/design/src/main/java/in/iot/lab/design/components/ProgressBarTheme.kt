package `in`.iot.lab.design.components

import androidx.compose.ui.graphics.Color

data class ProgressBarTheme(
    val background: Color,
    val border: Color,
    val borderHighlight: Color,
    val progress: Color,
    val progressHighlight: Color,
)

val SpaceProgressTheme = ProgressBarTheme(
    background = Color(0xFF09054D),
    border = Color(0xFF161162),
    borderHighlight = Color(0xFF1975BF),
    progress = Color(0xFFB6A815),
    progressHighlight = Color(0xFFAFBB92),
)
val RedProgressTheme = ProgressBarTheme(
    background = Color(0xFF410308),
    border = Color(0xFF72050E),
    borderHighlight = Color(0xFFF36974),
    progress = Color(0xFFCC2936),
    progressHighlight = Color(0xFFFFFFFF),
)
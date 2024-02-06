package `in`.iot.lab.dashboard.ui.screen.team.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun ThemedProgressBar(
    progress: Float, modifier: Modifier,
    colorScheme: ProgressBarTheme
) {
    Canvas(
        modifier
            .height(40.dp)
            .progressSemantics(progress)
    ) {
        val drawStuff: (Float, Float, Float, Color) -> Unit = { prog, thic, off, color ->
            drawLine(
                color,
                Offset(size.height / 2, size.height / 2 - size.height * off),
                Offset(size.width * prog - size.height / 2, size.height / 2 - size.height * off),
                size.height * thic, StrokeCap.Round
            )
        }
        drawStuff(1f, 1f, 0f, colorScheme.border)
        drawStuff(1f, .7f, 0f, colorScheme.background)
        drawStuff(progress, .7f, 0f, colorScheme.progress)
        drawStuff(progress, .3f, .1f, colorScheme.progressHighlight)
        drawStuff(1f, .15f, .4f, colorScheme.borderHighlight)
    }
}
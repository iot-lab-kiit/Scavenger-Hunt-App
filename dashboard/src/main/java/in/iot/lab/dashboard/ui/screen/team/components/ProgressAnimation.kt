package `in`.iot.lab.dashboard.ui.screen.team.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.imageResource
import `in`.iot.lab.design.R
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

@Composable
fun ProgressAnimation(
    mainProgressStatus: List<Boolean>,
    subsidiaryProgressStatus: List<Boolean>,

    modifier: Modifier = Modifier
) {
    val arcRatio = .6f
    val cometSpeed = 0.5f


    val trailColors = listOf(
        Color(0xFF2FA086), Color(0xFF99498F),
        Color(0xFFE29021), Color(0xFFCF403A)
    )
    val centerRadius = 0.07f
    val arcAngle = arcRatio * 360f / mainProgressStatus.size
    val arcSpace = (1 - arcRatio) * 360f / mainProgressStatus.size
    val arcRadius = 0.12f
//    val arcRadius = 0.5f
    val comets = remember {
        subsidiaryProgressStatus.mapIndexed { i, it ->
            Comet(
                360f * i / subsidiaryProgressStatus.size,
                it, trailColors.random()
            )
        }.toList()
    }
    val minCometDistance = 0.2f
    val cometRadius = 0.01f
    val cometTrail = 80f
    val glow = ImageBitmap.imageResource(R.drawable.glow)


    var a by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = a) {
        a += 0.01f
        comets.forEach { it.angle += cometSpeed * it.speed }
    }
    Canvas(modifier = modifier.fillMaxSize()) {
        val center = Offset(size.width / 2, size.width / 2)
        val strokeWidth = size.width * 0.005f
        val topLeft = center - Offset(size.width * arcRadius, size.width * arcRadius)
        val arcSize = Size(size.width * arcRadius * 2, size.width * arcRadius * 2)

        val dummy = 1f
        val topLeftGlaze = center - Offset(size.width * dummy, size.width * dummy)
        val arcSizeGlaze = Size(size.width * dummy * 2, size.width * dummy * 2)
        val cometMagnitude = size.width * minCometDistance

        val drawProgress: (Int, Boolean) -> Unit = { it, on ->
            val startAngle = it * (arcAngle + arcSpace) - 90f - arcAngle / 2
            if (on) drawArc(
                Color(0x33F86E8E), startAngle, arcAngle, false, topLeftGlaze,
                arcSizeGlaze, style = Stroke(1.75f * size.width, cap = StrokeCap.Butt)
            )
            drawArc(
                Color.White, startAngle, arcAngle, false, topLeft, arcSize,
                style = Stroke(if (on) 3 * strokeWidth else strokeWidth, cap = StrokeCap.Round)
            )
        }

        val drawTrail: (Comet, Float, Color) -> Unit = { it, width, color ->
            val distance = cometMagnitude + (size.width / 2 - cometMagnitude) * it.distance
            drawArc(
                Brush.radialGradient(
                    0f to color, .1f to Color.Transparent, 1f to Color.Transparent,
                    center = center + Offset(
                        distance * cos(it.angle * 0.017452778f),
                        distance * sin(it.angle * 0.017452778f)
                    ),
                    radius = distance * tan(cometTrail * 0.017452778f)
                ),
                it.angle, -cometTrail, false,
                center - Offset(distance, distance),
                Size(2 * distance, 2 * distance),
                style = Stroke(width * size.width)
            )
        }

        val drawComet: (Comet, Float, Color) -> Unit = { it, radius, color ->
            val distance = cometMagnitude + (size.width / 2 - cometMagnitude) * it.distance
            val position = center + Offset(
                distance * cos(it.angle * 0.017452778f),
                distance * sin(it.angle * 0.017452778f)
            )
            drawCircle(color, radius * size.width, position)
            if (it.isDone)
                drawImage(glow, position - Offset(glow.width / 2f, glow.height / 2f))
        }

        drawCircle(Color(0x3326007A), size.width * (centerRadius + 0.5f), center)
        drawCircle(Color(0x33730F83), size.width * (centerRadius + 0.375f), center)
        drawCircle(Color(0x33CF406B), size.width * (centerRadius + 0.175f), center)
        drawCircle(Color(0xAAF38353), size.width * (centerRadius + 0.075f), center)
        drawCircle(Color(0xFFF9D962), size.width * (centerRadius + 0.025f), center)
        drawCircle(Color.White, size.width * centerRadius, center)
        mainProgressStatus.forEachIndexed { i, b -> drawProgress(i, b) }
        comets.forEach { drawTrail(it, 3 * cometRadius, it.offColor) }
        comets.forEach { drawComet(it, 1.5f * cometRadius, it.offColor) }
        comets.forEach { drawTrail(it, cometRadius, it.color) }
        comets.forEach { drawComet(it, cometRadius, Color.White) }
    }
}
package `in`.iot.lab.dashboard.ui.screen.team

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.dashboard.R
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

@Composable
internal fun TeamScreen() {
    // GET This data from the API
    val mainQuests = remember { (1..10).map { Math.random() < .5 }.toList() }
    val sideQuests = remember { (1..10).map { Math.random() < .5 }.toList() }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.matrix),
            contentDescription = "Bottom",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Spacer(modifier = Modifier.size(100.dp))
            ProgressAnimation(
                mainQuests, sideQuests,
                Modifier
                    .fillMaxWidth(0.7f)
                    .aspectRatio(1f)
            )
            Column(
                modifier = Modifier.padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val textStyle = TextStyle(
                    color = Color.White, fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.orbitron_regular))
                )
                Text(text = "Main Quest", style = textStyle)
                val mainProgress = mainQuests.map { if (it) 1f else 0f }.average().toFloat()
                ThemedProgressBar(
                    progress = mainProgress,
                    modifier = Modifier.fillMaxWidth(),
                    RedProgressTheme
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "Side Quest", style = textStyle)
                val sideProgress = sideQuests.map { if (it) 1f else 0f }.average().toFloat()
                ThemedProgressBar(
                    progress = sideProgress,
                    modifier = Modifier.fillMaxWidth(),
                    RedProgressTheme
                )
            }
        }
    }
}

data class Comet(var angle: Float, var isDone: Boolean, val color: Color) {
    var distance = (4 * Math.random()).toInt() / 4f
    val offColor = lerp(color, Color.Black, 0.3f)
    val speed = 1f + Math.random().toFloat() * 0.3f
}

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


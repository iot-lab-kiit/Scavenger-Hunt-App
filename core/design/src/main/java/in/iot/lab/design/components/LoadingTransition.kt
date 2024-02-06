package `in`.iot.lab.design.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.R

@Composable
fun LoadingTransition(
    textDurationMillis: Int,
    modifier: Modifier = Modifier
) {
    val list = remember {
        listOf(
            "Fetching Chests", "Contacting Pirates", "Unraveling Mysteries", "Charting Waters",
            "Assembling Crewmates", "Resolving Routes", "Unearthing Relics", "Navigating Storms",
            "Scanning Horizons", "Hoisting Sails", "Searching Maps", "Battling Monsters",
            "Brewing Ale",
        ).shuffled()
    }
    val textStyle = TextStyle(
        color = Color.White, fontSize = 20.sp,
        fontFamily = FontFamily(Font(R.font.orbitron_regular))
    )
    val transition = rememberInfiniteTransition("Loading Screen Transition")
    var progress by remember { mutableStateOf(0f) }
    var sudo by remember { mutableStateOf(false) }
    var indexA by remember { mutableStateOf(0) }
    var indexB by remember { mutableStateOf(0) }
    val index by transition.animateFloat(
        initialValue = 0f, targetValue = list.size.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                textDurationMillis * list.size, easing = LinearEasing
            )
        ),
        label = "Text"
    )
    progress += (1f - progress) * 0.003f + 0f * index
    LaunchedEffect(index.toInt()) {
        sudo = !sudo
        if (sudo) indexA = index.toInt() % list.size
        else indexB = index.toInt() % list.size
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        ThemedProgressBar(
            progress = progress, modifier = modifier.fillMaxWidth(), colorScheme = RedProgressTheme
        )
        Spacer(modifier.size(10.dp))
        Box(modifier.fillMaxWidth()) {
            androidx.compose.animation.AnimatedVisibility(
                modifier = modifier.fillMaxWidth(), visible = sudo,
                enter = slideIn(initialOffset = { IntOffset(0, -it.height) }) + fadeIn(),
                exit = slideOut(targetOffset = { IntOffset(0, it.height / 2) }) + fadeOut()
            ) {
                Text(text = list[indexA], style = textStyle, textAlign = TextAlign.Center)
            }
            androidx.compose.animation.AnimatedVisibility(
                modifier = modifier.fillMaxWidth(), visible = !sudo,
                enter = slideIn(initialOffset = { IntOffset(0, -it.height) }) + fadeIn(),
                exit = slideOut(targetOffset = { IntOffset(0, it.height / 2) }) + fadeOut()
            ) {
                Text(text = list[indexB], style = textStyle, textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview
@Composable
fun Preview(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp)
    ) {
        LoadingTransition(
            2000,
            modifier = modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        )
    }
}
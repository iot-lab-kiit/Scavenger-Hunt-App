package `in`.iot.lab.design.components

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.R
import `in`.iot.lab.design.theme.ScavengerHuntTheme


// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    ScavengerHuntTheme {
        AppScreen(modifier = Modifier.fillMaxSize()) {
            LoadingTransition(
                modifier = Modifier.fillMaxWidth(),
                textDurationMillis = 1000
            )
        }
    }
}


/**
 * This is the Loading Animation to be used in the Whole App.
 *
 * @param modifier This is the optional [Modifier] which can be passed from the Parent.
 * @param textDurationMillis This is the Time according to which texts shall switch and loads.
 */
@Composable
fun LoadingTransition(
    modifier: Modifier = Modifier,
    textDurationMillis: Int = 1000
) {

    // List of Texts to show the User
    val list = remember {
        listOf(
            "Fetching Chests", "Contacting Pirates", "Unraveling Mysteries", "Charting Waters",
            "Assembling Crew mates", "Resolving Routes", "Unearthing Relics", "Navigating Storms",
            "Scanning Horizons", "Hoisting Sails", "Searching Maps", "Battling Monsters",
            "Brewing Ale",
        ).shuffled()
    }

    // Default Text Style
    val textStyle = TextStyle(
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.orbitron_regular)),
        textAlign = TextAlign.Center,
        letterSpacing = TextUnit(value = 2f, type = TextUnitType.Sp)
    )

    // Transition Type is infinite
    val transition = rememberInfiniteTransition("Loading Screen Transition")

    // This keeps the Progress Bar float data
    var progress by remember { mutableFloatStateOf(0f) }

    // This determines which text among the two ticking shall be shown
    var sudo by remember { mutableStateOf(false) }

    // First and Second Text Index value
    var indexA by remember { mutableIntStateOf(0) }
    var indexB by remember { mutableIntStateOf(0) }

    // This index value changes every given time and triggers the Text changing Animation
    val index by transition.animateFloat(
        initialValue = 0f,
        targetValue = list.size.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = textDurationMillis * list.size,
                easing = LinearEasing
            )
        ),
        label = "Text"
    )

    // Progress calculator to slow the progress with time
    progress += (1f - progress) * 0.003f + 0f * index

    // Changing state according to the index value generated each trigger
    LaunchedEffect(index.toInt()) {
        sudo = !sudo
        if (sudo)
            indexA = index.toInt() % list.size
        else
            indexB = index.toInt() % list.size
    }

    AppScreen {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            // Progress Bar
            ThemedProgressBar(
                progress = progress,
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                colorScheme = RedProgressTheme
            )

            // Box which shows the Texts
            Box(modifier.fillMaxWidth()) {

                // First Text Field
                androidx.compose.animation.AnimatedVisibility(
                    modifier = modifier.fillMaxWidth(),
                    visible = sudo,
                    enter = slideIn(initialOffset = { IntOffset(0, -it.height) }) + fadeIn(),
                    exit = slideOut(targetOffset = { IntOffset(0, it.height / 2) }) + fadeOut()
                ) {
                    Text(
                        text = list[indexA],
                        style = textStyle
                    )
                }

                // Second Text Field
                androidx.compose.animation.AnimatedVisibility(
                    modifier = modifier.fillMaxWidth(),
                    visible = !sudo,
                    enter = slideIn(initialOffset = { IntOffset(0, -it.height) }) + fadeIn(),
                    exit = slideOut(targetOffset = { IntOffset(0, it.height / 2) }) + fadeOut()
                ) {
                    Text(
                        text = list[indexB],
                        style = textStyle
                    )
                }
            }
        }
    }
}
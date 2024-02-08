package `in`.iot.lab.dashboard.ui.screen.team.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.RedProgressTheme
import `in`.iot.lab.design.components.ThemedProgressBar
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
        AppScreen {
            TextAndProgressUI(text = "Main Quest", progress = .8f)
        }
    }
}


/**
 * This function is used to create the text with the Progress bar UI in the Team Screen.
 *
 * @param text This contains the text that needs to be shown in the UI.
 * @param progress This is the progress in [Float] from 0f to 1f.
 */
@Composable
fun TextAndProgressUI(
    text: String,
    progress: Float
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Main or Side Quest Text
        Text(
            text = text,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.orbitron_regular))
            )
        )

        // Progress Bar
        ThemedProgressBar(
            progress = progress,
            modifier = Modifier.fillMaxWidth(),
            colorScheme = RedProgressTheme
        )
    }
}
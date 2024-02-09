package `in`.iot.lab.leaderboard.view.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import `in`.iot.lab.leaderboard.R


/**
 * This is the header for the Leader board screen.
 *
 * @param modifier This is made to pass modifier from the parent function
 * @param text This is the text to be shown in the header.
 */
@Composable
fun HeaderText(
    modifier: Modifier,
    text: String
) {

    // Default Header text
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Start,
        fontFamily = FontFamily(Font(R.font.montserratsemibold)),
        style = MaterialTheme.typography.headlineMedium
    )
}
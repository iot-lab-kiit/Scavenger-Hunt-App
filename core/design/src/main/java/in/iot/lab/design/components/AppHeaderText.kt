package `in`.iot.lab.design.components


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import `in`.iot.lab.design.R


/**
 * This is the header text which is used to denote the header of the App Screens.
 *
 * @param modifier Optional modifier that can be passed from the parent function
 * @param text This is the text that will be shown.
 */
@Composable
fun AppHeaderText(
    modifier: Modifier = Modifier,
    text: String
) {

    Text(
        modifier = modifier,
        text = text,
        fontFamily = FontFamily(Font(R.font.montserratsemibold)),
        style = MaterialTheme.typography.headlineMedium,
        overflow = TextOverflow.Ellipsis
    )
}
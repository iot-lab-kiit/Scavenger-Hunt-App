package `in`.iot.lab.leaderboard.view.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import `in`.iot.lab.leaderboard.R


@Composable
fun HeaderText(text: String,
               modifier: Modifier) {
    Text(text = text,
        color = Color(0xffcc2936),
        textAlign = TextAlign.Start,
        fontFamily = FontFamily(Font(R.font.montserratsemibold)),
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier)
}
package `in`.iot.lab.dashboard.ui.screen.team_details.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.PrimaryButton
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
        ThemeCardUI(theme = "This is the theme", themeDoc = "Text")
    }
}


@Composable
fun ThemeCardUI(
    theme: String,
    themeDoc: String
) {

    val uriHandler = LocalUriHandler.current

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        border = BorderStroke(
            width = 3.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Your Theme Text
            Text(
                text = "YOUR THEME",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily(Font(R.font.orbitron_regular)),
                    letterSpacing = 2.sp,
                )
            )


            // User Team's Theme
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = theme,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.montserratsemibold))
                )
            )


            // Visit Documentation button
            PrimaryButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    uriHandler.openUri(themeDoc)
                },
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "View Doc")
            }
        }
    }
}
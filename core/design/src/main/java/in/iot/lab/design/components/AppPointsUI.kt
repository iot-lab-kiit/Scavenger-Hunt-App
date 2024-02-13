package `in`.iot.lab.design.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        AppPointsUI(text = "4567")
    }
}


/**
 * This function is used to show the points scored.
 *
 * @param text This is the point that the user team has gathered.
 */
@Composable
fun AppPointsUI(text: String) {

    PrimaryButton(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        onClick = {},
        contentPadding = PaddingValues(8.dp),
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                // Points
                Text(text = text)

                // Iot Image
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.iot_logo),
                    contentDescription = "IoT Logo"
                )
            }
        }
    )
}
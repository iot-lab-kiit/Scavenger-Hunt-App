package `in`.iot.lab.credits.view.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.credits.data.models.RemoteAboutUs
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
        CreditsCard(
            aboutUs = RemoteAboutUs(
                name = "Anirban BasakXXXXXX XXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXX",
                designation = "Android Developer",
                githubLink = "Link",
            )
        )
    }
}


@Composable
fun CreditsCard(aboutUs: RemoteAboutUs) {

    val uriHandler = LocalUriHandler.current

    // Bordered Card
    OutlinedCard(
        border = BorderStroke(
            width = 3.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(16.dp)
    ) {

        // Parent Row
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Keeps the Name and the Designation
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {

                // Name
                Text(
                    text = aboutUs.name ?: "Name",
                    fontSize = 18.sp,
                    fontFamily = FontFamily((Font(R.font.montaga_regular)))
                )

                // Designation / Role
                Text(
                    text = aboutUs.designation ?: "Designation",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontFamily = FontFamily((Font(R.font.montserrat_regular)))
                )
            }


            // Composable containing the Github and LinkedIn Icons
            Row(verticalAlignment = Alignment.CenterVertically) {

                // Github Icon
                aboutUs.githubLink?.let {
                    IconButton(
                        onClick = {
                            uriHandler.openUri(it)
                        }
                    ) {
                        Image(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.github_logo),
                            contentDescription = "Github Logo"
                        )
                    }
                }

                // Linked In Icon
                aboutUs.linkedIn?.let {
                    IconButton(
                        onClick = {
                            uriHandler.openUri(it)
                        }
                    ) {
                        Image(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.linkedin_logo),
                            contentDescription = "LinkedIn Logo"
                        )
                    }
                }
            }
        }
    }
}
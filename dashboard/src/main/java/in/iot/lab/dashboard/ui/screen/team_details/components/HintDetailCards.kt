package `in`.iot.lab.dashboard.ui.screen.team_details.components


import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.design.theme.darkBackGround
import `in`.iot.lab.network.data.models.hint.RemoteHint


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
        HintDetailCards(
            hintData = RemoteHint(
                question = "This is the Test Question.",
                image = "Image Url"
            )
        )
    }
}

@Composable
fun HintDetailCards(hintData: RemoteHint) {

    val uriHandler = LocalUriHandler.current

    AppScreen {

        OutlinedCard(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            border = BorderStroke(
                width = 2.5.dp,
                color = MaterialTheme.colorScheme.primary
            ),
            colors = CardDefaults.outlinedCardColors(containerColor = darkBackGround)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Hint text
                Text(
                    text = "Hint",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.orbitron_regular)),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                )


                // Bulb Icon
                if (hintData.image == null)
                    Image(
                        painter = painterResource(id = R.drawable.light_bulb),
                        contentDescription = "Hint Light Bulb Image"
                    )

                // Image For hint
                hintData.image?.let {
                    AsyncImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .size(200.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(hintData.image)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.light_bulb),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }


                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onSurface,
                        contentColor = MaterialTheme.colorScheme.surface
                    )
                ) {

                    // Hint Question Text
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = hintData.question ?: "Hint Question",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.montserratsemibold)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF4B4B4B),
                            textAlign = TextAlign.Center,
                        )
                    )
                }

                if (hintData.type?.contains("main") == true && !hintData.answer.isNullOrBlank())
                    PrimaryButton(
                        onClick = { uriHandler.openUri(hintData.answer ?: "") }
                    ) {
                        Text(text = "View Rewards")
                    }
            }
        }
    }
}
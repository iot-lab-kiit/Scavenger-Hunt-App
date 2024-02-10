package `in`.iot.lab.playgame.view.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.design.theme.darkBackGround


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
            RewardUI(onSkipClick = { /*TODO*/ }) {

            }
        }
    }
}

@Composable
fun RewardUI(
    onSkipClick: () -> Unit,
    onGoToHintClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = darkBackGround),
        border = BorderStroke(
            width = 3.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Local Image
            Image(
                painter = painterResource(id = R.drawable.reward),
                contentDescription = null,
                modifier = Modifier.height(120.dp)
            )


            // Text to Show
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Reward Unlocked",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle(R.font.orbitron_regular),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            )


            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "You may Go to the Reward or continue looking into the new hint" +
                        " you just received",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle(R.font.montserratsemibold),
                    textAlign = TextAlign.Center
                )
            )


            // Row containing Cancel and Try Again Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Cancel Button
                OutlinedButton(
                    onClick = onSkipClick,
                    modifier = Modifier.weight(1F),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(
                        text = "Skip",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }


                // Continue Button
                PrimaryButton(
                    onClick = onGoToHintClick,
                    modifier = Modifier.weight(1F),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = "Reward",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}
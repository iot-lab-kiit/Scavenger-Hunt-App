package `in`.iot.lab.dashboard.ui.screen.team_details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.network.data.models.hint.RemoteHint


@Composable
fun HintsCard(
    modifier: Modifier = Modifier,
    hints: List<RemoteHint>? = null,
    onShowDetailClick: (RemoteHint) -> Unit
) {

    OutlinedCard(
        modifier = modifier.padding(horizontal = 24.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        border = BorderStroke(
            width = 3.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        hints?.forEachIndexed { index, remoteHint ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Hint Text
                val hintText = remoteHint.type?.get(0)?.uppercase() +
                        remoteHint.type?.substring(1) + " Hint ${index + 1}"

                Text(
                    text = hintText,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.orbitron_regular)),
                        letterSpacing = 2.sp,
                    )
                )

                // Visit Reward button
                PrimaryButton(
                    onClick = {
                        onShowDetailClick(remoteHint)
                    },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "View")
                }

            }
            Divider(
                color = MaterialTheme.colorScheme.primary,
                thickness = 3.dp
            )
        }
    }
}


@Composable
@Preview
fun HintsCardPreview() {
    val mockHint = listOf(
        RemoteHint(
            id = "1",
            answer = "This is a hint",
            campus = 1,
            question = "What is this?",
            type = "main"
        ),
        RemoteHint(
            id = "2",
            answer = "This is a hint",
            campus = 1,
            question = "What is this?",
            type = "main"
        ),
        RemoteHint(
            id = "3",
            answer = "This is a hint",
            campus = 1,
            question = "What is this?",
            type = "main"
        ),
        RemoteHint(
            id = "4",
            answer = "This is a hint",
            campus = 1,
            question = "What is this?",
            type = "main"
        ),
        RemoteHint(
            id = "5",
            answer = "This is a hint",
            campus = 1,
            question = "What is this?",
            type = "main"
        )
    )
    ScavengerHuntTheme {
        AppScreen {
            HintsCard(hints = mockHint) { _ -> }
        }
    }
}
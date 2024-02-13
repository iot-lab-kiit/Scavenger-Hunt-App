package `in`.iot.lab.dashboard.ui.screen.team_details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.SecondaryButton
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.network.data.models.hint.RemoteHint


@Composable
fun HintsCard(
    modifier: Modifier = Modifier,
    hints: List<RemoteHint>? = null,
    onShowDetailClick: (RemoteHint) -> Unit
) {

    Card(
        modifier = modifier.padding(horizontal = 24.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        border = BorderStroke(
            width = 3.dp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        hints?.forEachIndexed { index, _ ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                Color.Black.copy(alpha = 0.4f),
                            ),
                            angleInDegrees = 60f
                        )
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Hint Text
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    text = "Hint ${index + 1}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily(Font(R.font.orbitron_regular)),
                        letterSpacing = 2.sp,
                    )
                )

                // Visit Reward button
                SecondaryButton(
                    onClick = {
                        onShowDetailClick(hints[index])
                    },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "View Details")
                }

                Divider(
                    color = MaterialTheme.colorScheme.onPrimary,
                    thickness = 3.dp
                )
            }
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
package `in`.iot.lab.dashboard.ui.screen.team_details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.SecondaryButton
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.network.data.models.hint.RemoteHint

@Composable
fun HintsCard(
    modifier: Modifier = Modifier,
    hints: List<RemoteHint>? = null,
) {

    val uriHandler = LocalUriHandler.current

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
                    )
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hint ${index + 1}",
                    modifier = Modifier.weight(9f)
                )
                SecondaryButton(
                    modifier = Modifier.size(60.dp, 25.dp),
                    onClick = {
                        uriHandler.openUri(hints[index].answer ?: "")
                    },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "View",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
            Divider(
                color = MaterialTheme.colorScheme.onPrimary,
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
            HintsCard(hints = mockHint)
        }
    }
}
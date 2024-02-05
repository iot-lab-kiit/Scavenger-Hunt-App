package `in`.iot.lab.dashboard.ui.screen.team.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.theme.ScavengerHuntTheme

@Composable
fun TeamTopBar(
    teamName: String = "Team 1",
    teamScore: Int = 0,
    onTeamScoreClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(0.8f),
            text = teamName,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontFamily = FontFamily(Font(R.font.orbitron_regular))
            ),
            letterSpacing = 4.sp
        )
        PrimaryButton(
            modifier = Modifier.size(60.dp, 30.dp).weight(0.15f),
            onClick = onTeamScoreClick,
            contentPadding = PaddingValues(0.dp),
            content = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = teamScore.toString())
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.iot_logo),
                        contentDescription = "Points Logo"
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun TeamTopBarPreview() {
    ScavengerHuntTheme {
        TeamTopBar()
    }
}
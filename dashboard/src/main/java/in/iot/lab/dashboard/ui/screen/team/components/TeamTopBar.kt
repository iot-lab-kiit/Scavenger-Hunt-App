package `in`.iot.lab.dashboard.ui.screen.team.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
    onNavigateToTeamDetails: () -> Unit = {}
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
        TeamHeader(
            modifier = Modifier.weight(0.8f),
            teamName = teamName,
            onClick = onNavigateToTeamDetails
        )
        Spacer(modifier = Modifier.weight(0.05f))
        PrimaryButton(
            modifier = Modifier
                .size(60.dp, 30.dp)
                .weight(0.15f),
            onClick = {},
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


@Composable
private fun TeamHeader(
    modifier: Modifier,
    teamName: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = teamName,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontFamily = FontFamily(Font(R.font.orbitron_regular))
            ),
            letterSpacing = 4.sp
        )

        PrimaryButton(
            modifier = Modifier.size(28.dp),
            onClick = onClick,
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(8.dp),

        ) {
            Icon(
                painter = painterResource(`in`.iot.lab.dashboard.R.drawable.ic_arrow_forward),
                modifier = Modifier.size(16.dp),
                contentDescription = "Go to Team Details",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
package `in`.iot.lab.dashboard.ui.screen.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.dashboard.R
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.design.theme.background
import `in`.iot.lab.network.data.models.team.RemoteTeam

@Composable
internal fun TeamRoute() {
    TeamScreen()
}

@Composable
internal fun TeamScreen(
    mainQuests: List<Boolean> = remember { (1..10).map { Math.random() < .5 }.toList() },
    sideQuests: List<Boolean> = remember { (1..10).map { Math.random() < .5 }.toList() },
    team: RemoteTeam = RemoteTeam(),
    onTeamScoreClick: () -> Unit = {}
) {
    // GET This data from the API

    Scaffold(
        containerColor = background,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        floatingActionButton = {
            PrimaryButton(
                modifier = Modifier
                    .padding(bottom = 110.dp)
                    .size(70.dp),
                onClick = {},
                contentPadding = PaddingValues(10.dp, 0.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.map),
                    contentDescription = "Map Icon"
                )
            }
        }
    ) {
        TeamScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            mainQuests = mainQuests,
            sideQuests = sideQuests,
            team = team,
            onTeamScoreClick = onTeamScoreClick
        )
    }
}

@Preview
@Composable
private fun TeamScreenPreview() {
    ScavengerHuntTheme {
        TeamScreen()
    }
}
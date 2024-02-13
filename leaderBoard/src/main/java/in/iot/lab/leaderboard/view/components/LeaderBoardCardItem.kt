package `in`.iot.lab.leaderboard.view.components


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.R
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.network.data.models.team.RemoteTeam


// Preview Composable Function
@Preview(
    "Light",
    showBackground = true
)
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    ScavengerHuntTheme {
        // Call Function Here

        LeaderBoardCardItem(team = RemoteTeam(teamName = "Test Team 01", score = 1000))
    }
}


/**
 * This function is made to show each card in the leaderboard containing details of only one
 * team
 *
 * @param modifier This is the optional modifier that needs to be passed from the Parent.
 * @param team This is the data of one single team [RemoteTeam].
 */
@Composable
fun LeaderBoardCardItem(
    modifier: Modifier = Modifier,
    isTopTeam: Boolean = false,
    team: RemoteTeam
) {

    // Row Parent
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Team Name
            Text(text = team.teamName ?: "Team Name")

            // Crown Image
            if (isTopTeam) {
                Image(
                    painter = painterResource(id = R.drawable.crown),
                    contentDescription = "Top Team",
                )
            }
        }


        // Team Points
        team.score?.let {
            Text(text = it.toString())
        }

    }

    // Red Line between each card
    Divider(
        color = MaterialTheme.colorScheme.primary,
        thickness = 3.dp
    )
}
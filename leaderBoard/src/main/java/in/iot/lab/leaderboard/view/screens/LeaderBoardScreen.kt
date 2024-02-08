package `in`.iot.lab.leaderboard.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.leaderboard.view.components.HeaderText
import `in`.iot.lab.leaderboard.view.components.LeaderBoardCardItem
import `in`.iot.lab.leaderboard.view.event.LeaderBoardEvent
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState


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
        LeaderBoardScreenSuccess(
            teamList = listOf(
                RemoteTeam(teamName = "Team 01"),
                RemoteTeam(teamName = "Team 02"),
                RemoteTeam(teamName = "Team 03"),
                RemoteTeam(teamName = "Team 04"),
                RemoteTeam(teamName = "Team 05"),
                RemoteTeam(teamName = "Team 06"),
                RemoteTeam(teamName = "Team 07"),
                RemoteTeam(teamName = "Team 08"),
                RemoteTeam(teamName = "Team 09"),
                RemoteTeam(teamName = "Team 10")
            )
        )
    }
}


/**
 * This function is used to define the Leader Board Screen UI
 *
 * @param teamList This contains the list of teams in the leader board.
 * @param setEvent This is used to share the events with the View Model.
 * @param onBackPress This function is invoked when the user presses back button
 */
@Composable
fun LeaderBoardScreenControl(
    teamList: UiState<List<RemoteTeam>>,
    setEvent: (LeaderBoardEvent) -> Unit,
    onBackPress: () -> Unit
) {

    // Invoked every time the screen is called
    LaunchedEffect(Unit) {
        setEvent(LeaderBoardEvent.GetLeaderBoard)
    }

    // Checking the leader board team list data
    when (teamList) {

        // Idle State
        is UiState.Idle -> {
            setEvent(LeaderBoardEvent.GetLeaderBoard)
        }

        // Loading State
        is UiState.Loading -> {
            LoadingTransition()
        }

        // Success State
        is UiState.Success -> {
            LeaderBoardScreenSuccess(teamList = teamList.data)
        }

        // Error State
        is UiState.Failed -> {
            ErrorDialog(
                text = teamList.message,
                onTryAgain = {
                    setEvent(LeaderBoardEvent.GetLeaderBoard)
                },
                onCancel = onBackPress
            )
        }
    }
}


/**
 * This function is used to show the UI for the Leader board Screen when the api call
 * is successful.
 *
 * @param teamList This contains the list of teams in the leaderboard.
 */
@Composable
fun LeaderBoardScreenSuccess(teamList: List<RemoteTeam>) {

    // App Scaffold
    AppScreen(
        topBar = {
            HeaderText(
                text = "LEADERBOARD",
                modifier = Modifier.fillMaxWidth()
            )
        },
        contentAlignment = Alignment.TopCenter
    ) {

        // Leader Board Card Parent
        OutlinedCard(
            modifier = Modifier.padding(24.dp),
            colors = CardDefaults.outlinedCardColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            border = BorderStroke(
                width = 3.dp,
                color = MaterialTheme.colorScheme.primary
            )
        ) {

            // Scrollable list
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {

                items(teamList.size) {

                    // Each leader board entry card
                    LeaderBoardCardItem(team = teamList[it])
                }
            }
        }
    }
}
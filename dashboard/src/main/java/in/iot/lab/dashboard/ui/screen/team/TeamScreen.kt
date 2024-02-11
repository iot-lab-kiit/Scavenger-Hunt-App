package `in`.iot.lab.dashboard.ui.screen.team


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.iot.lab.dashboard.ui.screen.team.components.TeamScreenScaffoldUI
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState


@Composable
internal fun TeamRoute(
    viewModel: TeamScreenViewModel = hiltViewModel(),
    onTryAgainClick: () -> Unit,
    onCancelClick: () -> Unit,
    onNavigateToTeamDetails: () -> Unit,
    onNavigateToPlay: () -> Unit
) {
    val teamState by viewModel.teamData.collectAsState()

    when (teamState) {
        is UiState.Success -> {
            TeamScreen(
                team = (teamState as UiState.Success<RemoteTeam>).data,
                onNavigateToTeamDetails = onNavigateToTeamDetails,
                onNavigateToPlay = onNavigateToPlay
            )
        }

        is UiState.Loading -> {
            LoadingTransition()
        }

        is UiState.Failed -> {
            ErrorDialog(
                text = (teamState as UiState.Failed).message,
                onCancel = onCancelClick,
                onTryAgain = onTryAgainClick
            )
        }

        else -> {}
    }
}

@Composable
internal fun TeamScreen(
    team: RemoteTeam,
    onNavigateToTeamDetails: () -> Unit = {},
    onNavigateToPlay: () -> Unit = {}
) {

    // Custom Scaffold used for this Screen
    TeamScreenScaffoldUI(
        headerText = team.teamName ?: "Team Name",
        onNavigateToTeamDetails = onNavigateToTeamDetails,
        point = team.score?.toString() ?: "0",
        onNavigateToPlay = onNavigateToPlay
    ) {

        // Temp value so the compiler don't complain
        val tempMainQuests: List<Boolean> = remember { (1..10).map { Math.random() < .5 }.toList() }
        val tempSideQuests: List<Boolean> = remember { (1..10).map { Math.random() < .5 }.toList() }

        // Team Screen Contents are here !!
        TeamScreenContent(
            tempMainQuests = tempMainQuests,
            tempSideQuests = tempSideQuests,
            team = team
        )
    }
}

@Preview
@Composable
private fun TeamScreenPreview() {
    ScavengerHuntTheme {
        TeamScreen(team = RemoteTeam())
    }
}
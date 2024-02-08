package `in`.iot.lab.dashboard.ui.screen.team

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.iot.lab.dashboard.R
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.design.theme.background
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState

@Composable
internal fun TeamRoute(
    viewModel: TeamScreenViewModel = hiltViewModel(),
    onNavigateToTeamDetails: () -> Unit = {}
) {
    val teamState by viewModel.teamData.collectAsState()

    when (teamState) {
        is UiState.Success -> {
            val data = (teamState as UiState.Success<RemoteTeam>).data
            TeamScreen(
                team = data,
                onNavigateToTeamDetails = onNavigateToTeamDetails,
                mainQuests = data.mainQuest?.size ?: 0,
                // TODO: Add sideQuests here, currently its not implemented in the RemoteTeam model
                sideQuests = 10,
                mainQuestProgress = data.numMain ?: 0,
                sideQuestProgress = data.numSide ?: 0
            )
        }
        is UiState.Loading -> {
            CircularProgressIndicator()
        }
        is UiState.Failed -> {
            ErrorDialog(text = (teamState as UiState.Failed).message)
        }
        else -> {}
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun TeamScreen(
    mainQuests: Int = 10,
    sideQuests: Int = 10,
    mainQuestProgress: Int = 1,
    sideQuestProgress: Int = 3,
    team: RemoteTeam = RemoteTeam(),
    onNavigateToTeamDetails: () -> Unit = {}
) {
    Scaffold(
        containerColor = background,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        floatingActionButton = {
            PrimaryButton(
                modifier = Modifier
                    .padding(bottom = 10.dp)
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
        // Temp value so the compiler don't complain
        val tempMainQuests: List<Boolean> = remember { (1..10).map { Math.random() < .5 }.toList() }
        val tempSideQuests: List<Boolean> = remember { (1..10).map { Math.random() < .5 }.toList() }
        TeamScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            mainQuests = tempMainQuests,
            sideQuests = tempSideQuests,
//            mainQuestProgress = mainQuestProgress,
//            sideQuestProgress = sideQuestProgress,
            team = team,
            onNavigateToTeamDetails = onNavigateToTeamDetails
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
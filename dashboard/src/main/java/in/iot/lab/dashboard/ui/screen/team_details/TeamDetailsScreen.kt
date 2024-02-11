package `in`.iot.lab.dashboard.ui.screen.team_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.iot.lab.dashboard.ui.screen.team.TeamScreenViewModel
import `in`.iot.lab.dashboard.ui.screen.team_details.components.HintsCard
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.AppTopBar
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.design.components.TeamDetailsCard
import `in`.iot.lab.design.components.TeamMember
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.network.data.models.hint.RemoteHint
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.data.models.user.RemoteUser
import `in`.iot.lab.network.state.UiState

@Composable
internal fun TeamDetailsRoute(
    viewModel: TeamScreenViewModel = hiltViewModel(),
    onTryAgainClick: () -> Unit,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit = {}
) {
    val teamState by viewModel.teamData.collectAsState()

    when (teamState) {
        is UiState.Loading -> {
            LoadingTransition()
        }

        is UiState.Success -> {
            val data = (teamState as UiState.Success<RemoteTeam>).data
            TeamDetailsScreen(team = data, onBackClick = onBackClick)
        }

        is UiState.Failed -> {
            ErrorDialog(
                text = (teamState as UiState.Failed).message,
                onTryAgain = onTryAgainClick,
                onCancel = onCancelClick,
            )
        }

        else -> {}
    }
}

@Composable
internal fun TeamDetailsScreen(
    team: RemoteTeam = RemoteTeam(),
    onBackClick: () -> Unit = {}
) {
    AppScreen(
        topBar = {

            AppTopBar(
                headerText = team.teamName ?: "Team Name",
                onBackPress = onBackClick,
                pointDisplay = team.score?.toString() ?: "0",
            )
        }
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {

            item {
                TeamDetailsCard(
                    teamMember = team.teamMembers?.map { member ->
                        TeamMember(
                            name = member.name,
                            isLead = member.isLead
                        )
                    }
                )
            }

            item {
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = "UNLOCKED HINTS",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily(
                            Font(R.font.orbitron_regular)
                        ),
                        letterSpacing = 2.sp,
                    )
                )
            }

            // Hint Cards
            item {

                val hintList : MutableList<RemoteHint> = mutableListOf()
                team.mainQuest?.let { hintList.addAll(it) }
                team.sideQuest?.let { hintList.addAll(it) }

                HintsCard(hints = hintList)
            }
        }
    }
}

@Preview
@Composable
private fun TeamDetailsScreenPreview() {
    val mockTeam = RemoteTeam(
        teamName = "Team 1",
        teamLead = RemoteUser(name = "Member 1"),
        teamMembers = listOf(
            RemoteUser(name = "Member 1", isLead = true),
            RemoteUser(name = "Member 2"),
            RemoteUser(name = "Member 3"),
            RemoteUser(name = "Member 4"),
            RemoteUser(name = "Member 5")
        ),
        mainQuest = listOf(
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
    )
    ScavengerHuntTheme {
        TeamDetailsScreen(team = mockTeam)
    }

}
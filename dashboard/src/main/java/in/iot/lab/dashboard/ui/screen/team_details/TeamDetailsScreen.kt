package `in`.iot.lab.dashboard.ui.screen.team_details

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import `in`.iot.lab.dashboard.ui.screen.team.components.TeamTopBar
import `in`.iot.lab.dashboard.ui.screen.team_details.components.HintsCard
import `in`.iot.lab.design.components.TeamDetailsCard
import `in`.iot.lab.design.components.TeamMember
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.design.theme.background
import `in`.iot.lab.network.data.models.hint.RemoteHint
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.data.models.user.RemoteUser
import `in`.iot.lab.network.state.UiState

@Composable
internal fun TeamDetailsRoute(
    viewModel: TeamScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val teamState by viewModel.teamData.collectAsState()

    when (teamState) {
        is UiState.Success -> {
            val data = (teamState as UiState.Success<RemoteTeam>).data
            TeamDetailsScreen(team = data, onBackClick = onBackClick)
        }

        is UiState.Loading -> {
            CircularProgressIndicator()
        }

        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TeamDetailsScreen(
    team: RemoteTeam = RemoteTeam(),
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = background,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        topBar = {
            TopAppBar(
                title = {
                    TeamTopBar(
                        teamName = team.teamName ?: "Team Name",
                        teamScore = team.score ?: 0,
                        showNavigateToTeamDetails = false
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = background,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item { Spacer(modifier = Modifier.height(80.dp)) }
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
            item { Spacer(modifier = Modifier.height(28.dp)) }
            item {
                Text(
                    modifier = Modifier.padding(start = 25.dp),
                    text = "UNLOCKED HINTS",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily(
                            Font(`in`.iot.lab.design.R.font.orbitron_regular)
                        ),
                        letterSpacing = 2.sp,
                    )
                )
            }
            item { HintsCard(hints = team.mainQuest) }
        }
    }
}

@Preview
@Composable
private fun TeamDetailsScreenPreview() {
    val mockTeam = RemoteTeam(
        teamName = "Team 1",
        teamLead = RemoteUser(
            name = "Member 1",
            email = ""
        ),
        teamMembers = listOf(
            RemoteUser(
                name = "Member 1",
                email = "",
                isLead = true
            ),
            RemoteUser(
                name = "Member 2",
                email = ""
            ),
            RemoteUser(
                name = "Member 3",
                email = ""
            ),
            RemoteUser(
                name = "Member 4",
                email = ""
            ),
            RemoteUser(
                name = "Member 5",
                email = ""
            )
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
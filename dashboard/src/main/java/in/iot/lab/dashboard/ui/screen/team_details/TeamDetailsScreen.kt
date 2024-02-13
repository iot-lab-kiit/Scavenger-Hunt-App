package `in`.iot.lab.dashboard.ui.screen.team_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.iot.lab.dashboard.ui.screen.team.TeamScreenViewModel
import `in`.iot.lab.dashboard.ui.screen.team_details.components.HintsCard
import `in`.iot.lab.dashboard.ui.screen.team_details.components.ThemeCardUI
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
    onBackClick: () -> Unit = {},
    onShowDetailClick: (RemoteHint) -> Unit
) {
    val teamState by viewModel.teamData.collectAsState()

    when (teamState) {
        is UiState.Loading -> {
            LoadingTransition()
        }

        is UiState.Success -> {
            val data = (teamState as UiState.Success<RemoteTeam>).data
            TeamDetailsScreen(
                team = data,
                onBackClick = onBackClick,
                onShowDetailClick = onShowDetailClick
            )
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
    onBackClick: () -> Unit = {},
    onShowDetailClick: (RemoteHint) -> Unit
) {
    AppScreen(
        topBar = {

            AppTopBar(
                headerText = team.teamName ?: "Team Name",
                onBackPress = onBackClick,
                pointDisplay = team.score?.toString() ?: "0",
            )
        },
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

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
                ThemeCardUI(
                    theme = team.theme
                        ?: ("No theme is assigned to your team. Try again later or " +
                                "contact a Organising Member"),
                    themeDoc = team.themeDoc ?: ""
                )
            }


            // Unlocked Hints
            item {
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = "UNLOCKED HINTS",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily(Font(R.font.orbitron_regular)),
                        letterSpacing = 2.sp,
                    )
                )
            }


            team.mainQuest?.let {
                if (it.isNotEmpty())
                    item {
                        Text(
                            modifier = Modifier.padding(start = 32.dp),
                            text = "MAIN HINTS",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontFamily = FontFamily(Font(R.font.orbitron_regular)),
                                letterSpacing = 2.sp,
                            )
                        )
                    }
            }


            // Main Hints Cards
            team.mainQuest?.let {
                if (it.isNotEmpty())
                    item {
                        HintsCard(hints = it, onShowDetailClick = onShowDetailClick)
                    }
            }


            team.sideQuest?.let {
                if (it.isNotEmpty())
                    item {
                        Text(
                            modifier = Modifier.padding(start = 32.dp),
                            text = "SIDE HINTS",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontFamily = FontFamily(Font(R.font.orbitron_regular)),
                                letterSpacing = 2.sp,
                            )
                        )
                    }
            }


            // Side Quest Cards
            team.sideQuest?.let {
                if (it.isNotEmpty())
                    item {
                        HintsCard(hints = it, onShowDetailClick = onShowDetailClick)
                    }
            }
        }
    }
}

@Preview
@Composable
private fun TeamDetailsScreenPreview() {

    val hintList: MutableList<RemoteHint> = mutableListOf()
    for (i in 0..10) {
        hintList.add(RemoteHint())
    }

    val mockTeam = RemoteTeam(
        teamName = "Team 1",
        teamLead = RemoteUser(name = "Member 1"),
        teamMembers = listOf(
            RemoteUser(name = "Member 1", isLead = true),
            RemoteUser(name = "Member 2"), RemoteUser(name = "Member 3"),
            RemoteUser(name = "Member 4"), RemoteUser(name = "Member 5")
        ),
        mainQuest = hintList,
        theme = "This is your theme. sajkca kasj falskhf alk;sflak shklfash klfhaslkhf aslkhfas"
    )
    ScavengerHuntTheme {
        TeamDetailsScreen(team = mockTeam) { _ -> }
    }
}
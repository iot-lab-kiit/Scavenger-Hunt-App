package `in`.iot.lab.teambuilding.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.AppTopBar
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.SecondaryButton
import `in`.iot.lab.design.components.TeamDetailsCard
import `in`.iot.lab.design.components.TeamMember
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.data.models.user.RemoteUser
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.qrcode.generator.QrGenerator
import `in`.iot.lab.teambuilding.view.events.TeamBuildingEvent


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

        RegisterTeamSuccessScreen(
            team = RemoteTeam(
                id = "Team ID",
                teamMembers = listOf(
                    RemoteUser(name = "Test User 01", isLead = true),
                    RemoteUser(name = "Test User 02"),
                    RemoteUser(name = "Test User 03"),
                    RemoteUser(name = "Test User 04"),
                    RemoteUser(name = "Test User 05")
                )
            )
        ) {}
    }
}


@Composable
internal fun RegisterTeamScreenControl(
    teamDataState: UiState<RemoteTeam>,
    setEvent: (TeamBuildingEvent) -> Unit,
    onTeamRegistered: () -> Unit
) {

    when (teamDataState) {

        is UiState.Idle -> {
            setEvent(TeamBuildingEvent.NetworkIO.GetTeamData)
        }

        is UiState.Loading -> {
            LoadingTransition()
        }

        is UiState.Success -> {
            if (teamDataState.data.isRegistered == true)
                onTeamRegistered()
            else
                RegisterTeamSuccessScreen(team = teamDataState.data, setEvent = setEvent)
        }

        is UiState.Failed -> {
            ErrorDialog(
                text = teamDataState.message,
                onCancel = {}
            ) { setEvent(TeamBuildingEvent.NetworkIO.GetTeamData) }
        }
    }
}


/**
 * This UI is for the Registration UI.
 *
 * @param team This is the team data.
 * @param setEvent This is used to send events from the UI layer to the ViewModels
 */
@Composable
private fun RegisterTeamSuccessScreen(
    team: RemoteTeam,
    setEvent: (TeamBuildingEvent) -> Unit
) {

    // Default App Background
    AppScreen(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
        topBar = {

            // Team Name Header App Bar.
            AppTopBar(headerText = team.teamName ?: "Team Name")
        }
    ) {

        // Parent Composable UI
        LazyColumn(
            modifier = Modifier.padding(top = 40.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // QR Code
            item { QrGenerator(content = team.id!!) }

            // Showing the Team Members
            team.teamMembers?.let {
                item {

                    // Team List
                    val teamList: MutableList<TeamMember> = mutableListOf()

                    // Creating the List of members.
                    for (i in 0..4) {
                        if (it.size > i)
                            teamList.add(TeamMember(it[i].name, it[i].isLead))
                        else
                            teamList.add(TeamMember())
                    }

                    // Team Members List UI
                    TeamDetailsCard(teamMember = teamList)
                }
            }

            // Register and reload Button
            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    // Register Button
                    PrimaryButton(
                        modifier = Modifier.weight(1f),
                        enabled = true,
                        onClick = { setEvent(TeamBuildingEvent.NetworkIO.RegisterTeamApiCall) }
                    ) {
                        Text(text = "REGISTER")
                    }

                    // Reload Button
                    SecondaryButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            setEvent(TeamBuildingEvent.NetworkIO.GetTeamData)
                        }
                    ) {
                        Text(text = "Reload")
                    }
                }
            }
        }
    }
}
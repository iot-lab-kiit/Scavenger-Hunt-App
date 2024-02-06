package `in`.iot.lab.teambuilding.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.TheMatrixHeaderUI
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.qrcode.generator.QrGenerator
import `in`.iot.lab.teambuilding.view.events.TeamBuildingEvent


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


@Composable
private fun RegisterTeamSuccessScreen(
    team: RemoteTeam,
    setEvent: (TeamBuildingEvent) -> Unit
) {

    // Default App Background
    AppScreen(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // Parent Composable UI
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // The Matrix Header Composable
            item { TheMatrixHeaderUI() }

            item { Spacer(modifier = Modifier.height(50.dp)) }

            // QR Code
            item { QrGenerator(content = team.id!!) }

            // Showing the Team Members
            team.teamMembers?.let {
                items(it.size) {
//                    TODO("Call the UI for showing the Team Members")
                }
            }

            item {
                PrimaryButton(
                    enabled = true,
                    onClick = { setEvent(TeamBuildingEvent.NetworkIO.RegisterTeamApiCall) }
                ) {
                    Text(text = "REGISTER")
                }
            }
        }
    }
}
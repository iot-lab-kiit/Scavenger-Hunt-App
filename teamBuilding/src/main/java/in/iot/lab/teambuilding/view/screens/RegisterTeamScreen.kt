package `in`.iot.lab.teambuilding.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.TheMatrixHeaderUI
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.qrcode.generator.QrGenerator
import `in`.iot.lab.teambuilding.view.events.TeamBuildingEvent


@Composable
internal fun RegisterTeamScreenControl(
    createTeamState: UiState<RemoteTeam>,
    registerTeamState: UiState<RemoteTeam>,
    setEvent: (TeamBuildingEvent) -> Unit,
    onTeamRegistered: () -> Unit
) {

    when (registerTeamState) {

        is UiState.Idle -> {
            // Do Nothing
        }

        is UiState.Loading -> {
            CircularProgressIndicator()
        }

        is UiState.Success -> {
            onTeamRegistered()
        }

        is UiState.Failed -> {
            ErrorDialog(
                text = registerTeamState.message,
                onCancel = {}
            ) { setEvent(TeamBuildingEvent.NetworkIO.GetTeamData) }
        }
    }

    when (createTeamState) {
        is UiState.Idle -> {

        }

        is UiState.Loading -> {
            CircularProgressIndicator()
        }

        is UiState.Success -> {
            RegisterTeamSuccessScreen(team = createTeamState.data, setEvent = setEvent)
        }

        is UiState.Failed -> {
            ErrorDialog(
                text = createTeamState.message,
                onCancel = {}
            ) {
                setEvent(TeamBuildingEvent.NetworkIO.GetTeamData)
            }
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

            team.teamMembers?.let {
                items(it.size) {
                    TODO("Call the UI for showing the Team Members")
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
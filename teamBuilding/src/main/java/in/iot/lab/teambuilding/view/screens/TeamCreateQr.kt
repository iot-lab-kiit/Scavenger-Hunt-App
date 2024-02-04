package `in`.iot.lab.teambuilding.view.screens

import android.util.Log.d
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.qrcode.generator.QrGenerator
import `in`.iot.lab.teambuilding.view.components.AppFailureScreen
import `in`.iot.lab.teambuilding.view.components.AppScreen
import `in`.iot.lab.teambuilding.view.components.TheMatrixHeaderUI
import `in`.iot.lab.teambuilding.view.events.TeamBuildingEvent

@Composable
fun TeamCreateQr(
    teamName: String,
    createTeamState: UiState<String>,
    setEvent: (TeamBuildingEvent) -> Unit
) {

    // Calling the Api when the screen is composed first time
    LaunchedEffect(Unit) {
        setEvent(TeamBuildingEvent.CreateTeamApiCall(teamName = teamName))
    }

    // Default App Background
    AppScreen(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // Parent Composable UI
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // The Matrix Header Composable
            TheMatrixHeaderUI()

            Spacer(modifier = Modifier.height(50.dp))

            // QR Code
            when (createTeamState) {

                // Loading State
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }

                // Success State
                is UiState.Success -> {
                    d("Create Team Screen", createTeamState.data)
                    QrGenerator(content = createTeamState.data)
                }

                // Failed State
                is UiState.Failed -> {
                    AppFailureScreen(onCancel = {}) {
                        setEvent(TeamBuildingEvent.CreateTeamApiCall(teamName = teamName))
                    }
                }

                else -> {

                }
            }
        }
    }
}
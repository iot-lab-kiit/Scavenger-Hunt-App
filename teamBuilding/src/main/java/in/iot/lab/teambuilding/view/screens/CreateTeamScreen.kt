package `in`.iot.lab.teambuilding.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.TheMatrixHeaderUI
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.teambuilding.view.components.TeamBuildingOutlinedTextField
import `in`.iot.lab.teambuilding.view.events.TeamBuildingEvent
import `in`.iot.lab.teambuilding.view.navigation.TEAM_BUILDING_REGISTER_ROUTE

@Composable
internal fun CreateTeamScreenControl(
    teamName: String,
    createTeamState: UiState<String>,
    navController: NavController,
    setEvent: (TeamBuildingEvent) -> Unit
) {

    // Create Team Api State
    when (createTeamState) {

        // Idle State
        is UiState.Idle -> {
            CreateTeamIdleScreen(
                teamName = teamName,
                setEvent = setEvent
            )
        }

        // Loading State
        is UiState.Loading -> {
            CircularProgressIndicator()
        }

        // Success State
        is UiState.Success -> {
            navController.navigate(TEAM_BUILDING_REGISTER_ROUTE)
        }

        // Failed State
        is UiState.Failed -> {
            ErrorDialog(onCancel = {}) {
                setEvent(TeamBuildingEvent.CreateTeamApiCall)
            }
        }

    }
}

@Composable
private fun CreateTeamIdleScreen(
    teamName: String,
    setEvent: (TeamBuildingEvent) -> Unit
) {


    // Default Background
    AppScreen {

        // Parent Composable
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Matrix and Scavenger Hunt Image Header
            TheMatrixHeaderUI()

            Spacer(modifier = Modifier.height(150.dp))

            // Outlined Text Field for inputting team name
            TeamBuildingOutlinedTextField(
                modifier = Modifier.padding(horizontal = 32.dp),
                input = teamName,
                onValueChange = {
                    setEvent(TeamBuildingEvent.SetTeamName(it))
                },
                labelString = "Team Name"
            )

            // Create Team Button
            PrimaryButton(
                onClick = { setEvent(TeamBuildingEvent.CreateTeamApiCall) },
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth()
                    .height(height = 56.dp)
            ) {
                Text(text = "Create Team")
            }
        }
    }
}
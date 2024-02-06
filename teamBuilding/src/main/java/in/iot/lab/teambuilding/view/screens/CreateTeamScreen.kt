package `in`.iot.lab.teambuilding.view.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.TheMatrixHeaderUI
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.teambuilding.view.components.ConfirmDialogUI
import `in`.iot.lab.teambuilding.view.components.TeamBuildingOutlinedTextField
import `in`.iot.lab.teambuilding.view.events.TeamBuildingEvent


@Composable
internal fun CreateTeamScreenControl(
    teamName: String,
    createTeamState: UiState<RemoteTeam>,
    setEvent: (TeamBuildingEvent) -> Unit,
    onNavigateToRegistration: () -> Unit
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
            onNavigateToRegistration()
        }

        // Failed State
        is UiState.Failed -> {
            ErrorDialog(onCancel = {}) {
                setEvent(TeamBuildingEvent.NetworkIO.CreateTeamApiCall)
            }
        }

    }
}

@Composable
private fun CreateTeamIdleScreen(
    teamName: String,
    setEvent: (TeamBuildingEvent) -> Unit
) {

    var isCreateTeamLast by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Default Background
    AppScreen {

        AnimatedVisibility(visible = isCreateTeamLast) {
            Dialog(
                onDismissRequest = {
                    isCreateTeamLast = false
                }
            ) {

                ConfirmDialogUI(
                    text = "Are you Sure you want to Continue? You won't be able to Join " +
                            "Another Team after Joining one.",
                    imageId = R.drawable.server_error,
                    onDismiss = {
                        isCreateTeamLast = false
                    }
                ) {
                    setEvent(TeamBuildingEvent.NetworkIO.CreateTeamApiCall)
                }
            }
        }


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
                    setEvent(TeamBuildingEvent.Helper.SetTeamName(it))
                },
                labelString = "TEAM NAME"
            )

            // Create Team Button
            PrimaryButton(
                onClick = {
                    if (teamName == "" || teamName.isBlank() || teamName.isEmpty())
                        Toast.makeText(
                            context,
                            "Empty String is not allowed",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        isCreateTeamLast = true
                },
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth()
                    .height(height = 56.dp)
            ) {
                Text(text = "CREATE TEAM")
            }
        }
    }
}
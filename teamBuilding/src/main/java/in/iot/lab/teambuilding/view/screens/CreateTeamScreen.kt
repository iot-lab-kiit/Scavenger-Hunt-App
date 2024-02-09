package `in`.iot.lab.teambuilding.view.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppBackgroundImage
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.TheMatrixHeaderUI
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.teambuilding.view.components.ConfirmDialogUI
import `in`.iot.lab.teambuilding.view.components.TeamBuildingOutlinedTextField
import `in`.iot.lab.teambuilding.view.events.TeamBuildingEvent


// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    ScavengerHuntTheme {
        AppScreen {
            CreateTeamIdleScreen(teamName = "Test Team 01") {}
        }
    }
}


@Composable
internal fun CreateTeamScreenControl(
    teamName: String,
    createTeamState: UiState<RemoteTeam>,
    setEvent: (TeamBuildingEvent) -> Unit,
    onNavigateToRegistration: () -> Unit
) {

    // Default Background
    AppScreen {

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
                LoadingTransition()
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
}

@Composable
private fun CreateTeamIdleScreen(
    teamName: String,
    setEvent: (TeamBuildingEvent) -> Unit
) {

    // For Showing the Confirm Dialog UI
    var isCreateTeamLast by remember { mutableStateOf(false) }
    val context = LocalContext.current

    AppBackgroundImage()

    AnimatedVisibility(visible = isCreateTeamLast) {
        Dialog(onDismissRequest = { isCreateTeamLast = false }) {

            ConfirmDialogUI(
                text = "Are you Sure you want to Continue? You won't be able to Join " +
                        "Another Team after Joining one.",
                imageId = R.drawable.server_error,
                onDismiss = { isCreateTeamLast = false }
            ) {
                setEvent(TeamBuildingEvent.NetworkIO.CreateTeamApiCall)
            }
        }
    }


    // Parent Composable
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Matrix and Scavenger Hunt Image Header
        TheMatrixHeaderUI()

        // Outlined Text Field for inputting team name
        TeamBuildingOutlinedTextField(
            modifier = Modifier.padding(horizontal = 32.dp),
            input = teamName,
            onValueChange = {
                setEvent(TeamBuildingEvent.Helper.SetTeamName(it))
            },
            labelString = "Team Name"
        )

        // Create Team Button
        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .height(height = 56.dp),
            onClick = {
                if (teamName == "" || teamName.isBlank() || teamName.isEmpty())
                    Toast.makeText(context, "Empty String is not allowed", Toast.LENGTH_SHORT)
                        .show()
                else
                    isCreateTeamLast = true
            }
        ) {
            Text(text = "CREATE TEAM")
        }
    }
}
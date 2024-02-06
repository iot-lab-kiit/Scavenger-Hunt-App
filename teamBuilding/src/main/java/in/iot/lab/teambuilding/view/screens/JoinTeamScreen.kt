package `in`.iot.lab.teambuilding.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.qrcode.installer.ModuleInstallerState
import `in`.iot.lab.teambuilding.view.events.TeamBuildingEvent


/**
 * This function is the Join Team screen where the User would get an QR Scanner to scan their Team
 * QR Code and join the team.
 *
 * @param installState This defines the QR Scanner Module Download state
 * @param teamJoiningApiState This defines the Team Joining Api state
 * @param navController This is the nav Controller which helps to navigate to other screen
 * @param setEvent This is used to pass events to the View Model from the UI Layer
 */
@Composable
internal fun JoinTeamScreenControl(
    installState: ModuleInstallerState,
    teamJoiningApiState: UiState<String>,
    navController: NavController,
    setEvent: (TeamBuildingEvent) -> Unit
) {

    // Starting the Scanner Flow in the View Model
    LaunchedEffect(Unit) {
        setEvent(TeamBuildingEvent.ScannerIO.CheckScannerAvailability)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Checking the QR Module Install State
        when (installState) {

            // Currently Downloading
            is ModuleInstallerState.Downloading -> {

                // TODO :- Remove Progress Indicator and add normal Circular Progress Indicator
                LinearProgressIndicator(progress = installState.progress.toFloat())
            }

            // Download Failed
            is ModuleInstallerState.Failure -> {

                // Failure Screen
                ErrorDialog(
                    text = installState.exception.message.toString(),
                    onCancel = { navController.popBackStack() }
                ) {
                    setEvent(TeamBuildingEvent.ScannerIO.CheckScannerAvailability)
                }
            }

            else -> {

            }
        }

        // Checking the Team Joining Api State
        when (teamJoiningApiState) {

            // Idle state
            is UiState.Idle -> {

            }

            // Api Call is being queued
            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            // Api call is successful
            is UiState.Success<*> -> {

                // TODO :- Change screens
            }

            // Api Call Failed
            is UiState.Failed -> {

                // Failure Screen
                ErrorDialog(
                    text = teamJoiningApiState.message,
                    onCancel = { navController.popBackStack() }
                ) {
                    setEvent(TeamBuildingEvent.ScannerIO.CheckScannerAvailability)
                }
            }
        }
    }

}
package `in`.iot.lab.teambuilding.view.screens


import androidx.compose.runtime.Composable
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.qrcode.scanner.CodeScannerScreen
import `in`.iot.lab.teambuilding.view.events.TeamBuildingEvent


/**
 * This function is the Join Team screen where the User would get an QR Scanner to scan their Team
 * QR Code and join the team.
 *
 * @param teamJoiningApiState This defines the Team Joining Api state
 * @param popBackStack This is used to go back to the previous screen
 * @param onJoiningTeam This is used to navigate to the Register Team
 * @param setEvent This is used to pass events to the View Model from the UI Layer
 */
@Composable
internal fun JoinTeamScreenControl(
    teamJoiningApiState: UiState<RemoteTeam>,
    popBackStack: () -> Unit,
    onJoiningTeam: () -> Unit,
    setEvent: (TeamBuildingEvent) -> Unit
) {

    AppScreen {

        // Checking the Team Joining Api State
        when (teamJoiningApiState) {

            // Idle state
            is UiState.Idle -> {
                CodeScannerScreen(
                    onCodeScanned = {
                        setEvent(TeamBuildingEvent.NetworkIO.JoinTeam(it))
                    },
                    onFailure = {
                        setEvent(TeamBuildingEvent.ScannerIO.ScannerFailure)
                    }
                )
            }

            // Api Call is being queued
            is UiState.Loading -> {
                LoadingTransition()
            }

            // Api call is successful
            is UiState.Success<*> -> {
                onJoiningTeam()
            }

            // Api Call Failed
            is UiState.Failed -> {

                // Failure Screen
                ErrorDialog(
                    text = teamJoiningApiState.message,
                    onCancel = {
                        setEvent(TeamBuildingEvent.Helper.ResetTeamJoiningState)
                        popBackStack()
                    }
                ) {
                    setEvent(TeamBuildingEvent.Helper.ResetTeamJoiningState)
                }
            }
        }
    }
}
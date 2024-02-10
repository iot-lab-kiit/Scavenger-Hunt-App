package `in`.iot.lab.playgame.view.screens


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.network.data.models.hint.RemoteHint
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.playgame.view.event.PlayGameEvent


@Composable
fun PlayScannerScreenControl(
    teamData: UiState<RemoteTeam>,
    scannerState: UiState<RemoteHint>,
    navigateToHints: () -> Unit,
    popBackStack: () -> Unit,
    setEvent: (PlayGameEvent) -> Unit
) {

    // Starting the Scanner
    LaunchedEffect(Unit) {
        setEvent(PlayGameEvent.NetworkIO.GetTeamData)
    }

    // App Scaffold
    AppScreen {

        when (teamData) {

            is UiState.Idle -> {
                // Do Nothing
            }

            is UiState.Loading -> {
                LoadingTransition()
            }

            is UiState.Success -> {

                // Checking Scanner States.
                when (scannerState) {

                    is UiState.Idle -> {
                        setEvent(PlayGameEvent.ScannerIO.CheckScannerAvailability)
                    }

                    is UiState.Loading -> {
                        LoadingTransition()
                    }

                    is UiState.Success -> {
                        navigateToHints()
                    }

                    is UiState.Failed -> {
                        ErrorDialog(
                            text = scannerState.message,
                            onCancel = popBackStack,
                            onTryAgain = {
                                setEvent(PlayGameEvent.ScannerIO.CheckScannerAvailability)
                            }
                        )
                    }
                }
            }

            is UiState.Failed -> {
                ErrorDialog(
                    text = teamData.message,
                    onCancel = popBackStack,
                    onTryAgain = {
                        setEvent(PlayGameEvent.ScannerIO.CheckScannerAvailability)
                    }
                )
            }
        }

    }
}
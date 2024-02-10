package `in`.iot.lab.playgame.view.screens


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.network.data.models.hint.RemoteHint
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.playgame.view.event.PlayGameEvent


@Composable
fun PlayScannerScreenControl(
    scannerState: UiState<RemoteHint>,
    navigateToHints: () -> Unit,
    popBackStack: () -> Unit,
    setEvent: (PlayGameEvent) -> Unit
) {

    // Starting the Scanner
    LaunchedEffect(Unit) {
        setEvent(PlayGameEvent.ScannerIO.CheckScannerAvailability)
    }

    // App Scaffold
    AppScreen {

        // Checking Scanner States.
        when (scannerState) {

            is UiState.Idle -> {
                // Do Nothing
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
}
package `in`.iot.lab.playgame.view.screens


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.network.data.models.hint.RemoteHint
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.playgame.view.event.PlayGameEvent
import `in`.iot.lab.qrcode.scanner.QrScannerState


@Composable
fun PlayScannerScreenControl(
    scannerState: QrScannerState,
    hintData: UiState<RemoteHint>,
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

        when (scannerState) {

            // Cancelled State
            is QrScannerState.Cancelled -> {
                popBackStack()
            }

            // Success State
            is QrScannerState.Success -> {

                // Checking Hint Api Call Status.
                when (hintData) {

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
                            text = hintData.message,
                            onCancel = popBackStack,
                            onTryAgain = {
                                setEvent(PlayGameEvent.Helper.ResetScanner)
                            }
                        )
                    }
                }
            }

            is QrScannerState.Failure -> {
                ErrorDialog(
                    text = scannerState.exception.message.toString(),
                    onCancel = popBackStack,
                    onTryAgain = {
                        setEvent(PlayGameEvent.ScannerIO.CheckScannerAvailability)
                    }
                )
            }

            // Idle and Running State
            else -> {

            }
        }
    }
}
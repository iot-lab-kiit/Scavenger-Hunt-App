package `in`.iot.lab.playgame.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.playgame.view.event.PlayGameEvent
import `in`.iot.lab.qrcode.installer.ModuleInstallerState


@Composable
fun PlayScannerScreenControl(
    installState: ModuleInstallerState,
    popBackStack: () -> Unit,
    setEvent: (PlayGameEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(PlayGameEvent.ScannerIO.CheckScannerAvailability)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Checking the QR Module Install State
        when (installState) {

            // Currently Downloading
            is ModuleInstallerState.Downloading -> {
                CircularProgressIndicator()
            }

            // Download Failed
            is ModuleInstallerState.Failure -> {

                // Failure Screen
                ErrorDialog(
                    text = installState.exception.message.toString(),
                    onCancel = popBackStack
                ) {
                    setEvent(PlayGameEvent.ScannerIO.CheckScannerAvailability)
                }
            }

            else -> {

            }
        }
    }
}
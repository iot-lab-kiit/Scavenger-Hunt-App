package `in`.iot.lab.playgame.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import `in`.iot.lab.playgame.view.event.PlayGameEvent


@Composable
fun PlayScannerScreenControl(
    popBackStack: () -> Unit,
    setEvent: (PlayGameEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(PlayGameEvent.ScannerIO.CheckScannerAvailability)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Checking the QR Module Install State

    }
}
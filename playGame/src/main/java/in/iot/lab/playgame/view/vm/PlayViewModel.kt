package `in`.iot.lab.playgame.view.vm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.playgame.view.event.PlayGameEvent
import `in`.iot.lab.qrcode.scanner.QrCodeScanner
import `in`.iot.lab.qrcode.scanner.QrScannerState
import javax.inject.Inject


@HiltViewModel
class PlayViewModel @Inject constructor(
    private val qrCodeScanner: QrCodeScanner
) : ViewModel() {


    /**
     * This function starts the [QrCodeScanner] scanner and start to scan for QR Codes.
     */
    private fun startScanner() {
        qrCodeScanner.startScanner {
            when (it) {

                // User Cancelled The Scanner Scan
                is QrScannerState.Cancelled -> {
                    TODO("Handle Scanner Cancelled State")
                }

                // Scanner Scan is successful
                is QrScannerState.Success -> {
                    TODO("Handle Scanner Success State")
                }

                // Scanner scan is a failure
                is QrScannerState.Failure -> {
                    TODO("Handle Scanner Failure State")
                }

                else -> {
                    // Do Nothing
                }
            }
        }
    }


    /**
     * This function receives the events from the UI Layer and calls the Functions according to the
     * events received.
     *
     * @param event This is the [PlayGameEvent] sealed class object to pass the Events
     */
    fun uiListener(event: PlayGameEvent) {
        when (event) {

            is PlayGameEvent.ScannerIO.CheckScannerAvailability -> {
                startScanner()
            }
        }
    }
}
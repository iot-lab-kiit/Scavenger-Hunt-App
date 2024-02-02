package `in`.iot.lab.qrcode.scanner

import java.lang.Exception

/**
 * This sealed interface contains the States of the Qr Code Scanner [QrCodeScanner] which would help
 * to distinguish the different states and makes it easier to pass the states and handle the states.
 *
 * @property Idle This state defines that the [QrCodeScanner] object is currently Idle and not
 * scanning
 * @property Running This state defines that the [QrCodeScanner] object is currently scanning for
 * codes
 * @property Success This state defines that the [QrCodeScanner] object has scanned a QR Code
 * successfully and this would keep the data from the QR Code Scanned result.
 * @property Cancelled This state defines that the User has cancelled scanning the QR.
 * @property Failure This state defines that there is some issue internally in the [QrCodeScanner].
 */
sealed interface QrScannerState {
    data object Idle : QrScannerState
    data object Running : QrScannerState
    class Success(val code: String) : QrScannerState
    data object Cancelled : QrScannerState
    class Failure(val exception: Exception) : QrScannerState
}
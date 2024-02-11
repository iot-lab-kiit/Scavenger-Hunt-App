package `in`.iot.lab.qrcode.scanner

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning


/**
 * This class provides a implementation for scanning for the QR Codes
 *
 * @param context This is the context of the activity of which we are using to launch this code scanner
 */
class QrCodeScanner(context: Context) {


    /**
     * This variable contains all the options and configurations for the QR Code Scanner
     */
    private val qrCodeOptions: GmsBarcodeScannerOptions = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .enableAutoZoom()
        .allowManualInput()
        .build()


    /**
     * This is the Scanner Variable which is used to start scanning
     */
    private val scanner: GmsBarcodeScanner = GmsBarcodeScanning.getClient(context, qrCodeOptions)


    /**
     * This function starts scanning for the QR Code and checks if the scan is a success or failure
     *
     * @param handleScannerState This function passes the various [QrScannerState] to the parent
     * function which can then decide to show the UI or run some tasks accordingly.
     */
    fun startScanner(handleScannerState: (QrScannerState) -> Unit) {

        // Starting Scan
        scanner.startScan()

            // Success Listener
            .addOnSuccessListener { qrCode ->
                handleScannerState(QrScannerState.Success(code = qrCode.rawValue ?: ""))
            }

            // Cancel Listener
            .addOnCanceledListener {
                handleScannerState(QrScannerState.Cancelled)
            }

            // Failure Listener
            .addOnFailureListener {
                handleScannerState(QrScannerState.Failure(it))
            }
    }
}
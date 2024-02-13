package `in`.iot.lab.qrcode.scanner


import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView


/**
 * This is the Code Scanner Screen which opens the Scanner and scans the Qr and fetches the data.
 *
 * @param onCodeScanned This function is invoked when the code is scanned
 * @param onFailure This Function is invoked when there was a failure or User Cancelled the Scanner
 */
@Composable
fun CodeScannerScreen(
    onCodeScanned: (String) -> Unit,
    onFailure: () -> Unit
) {

    val context = LocalContext.current

    // Initialising the Scanner Module
    val compoundBarcodeView = remember {
        CompoundBarcodeView(context).apply {

            val capture = CaptureManager(context as Activity, this)
            capture.initializeFromIntent(context.intent, null)
            this.setStatusText("Scanning Qr Code")

            // Starting the  Scanner Preview and decoding
            this.resume()
            capture.decode()

            this.decodeSingle { result ->
                if (result.text != null)
                    onCodeScanned(result.text)
                else
                    onFailure()
            }
        }
    }

    // Parent Composable Function
    AndroidView(factory = { compoundBarcodeView })

    // This is used to pause or stop the Scanner after the Scanner finishes Scanning
    DisposableEffect(Unit) {
        onDispose {
            compoundBarcodeView.pause()
        }
    }
}
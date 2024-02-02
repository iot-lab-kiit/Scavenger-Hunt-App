package `in`.iot.lab.qrcode.generator

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * This function is used to generate a QR Code and also display it during the State Changes
 *
 * @param modifier This is the default Modifier to be passed from the Parent Class for the Image
 * @param content This is string value that would be shown when the Qr is Scanned
 * @param size This is the size of the Qr Code
 */
@Composable
fun QrGenerator(
    modifier: Modifier = Modifier,
    content: String,
    contentDescription: String? = null,
    loaderUI: @Composable () -> Unit = { CircularProgressIndicator(modifier) },
    size: Int = 512
) {

    // State Variable which shows if the Qr is generating or already generated
    var qrCodeGenerator by remember { mutableStateOf<QrGeneratorState>(QrGeneratorState.Generating) }

    // This Launched Effect is used to start making the Qr Bitmap during the first composition
    LaunchedEffect(Unit) {
        launch(Dispatchers.IO) {
            qrCodeGenerator = QrGeneratorState.Completed(generateQr(content, size))
        }
    }

    // Checking the Various States and showing the UI accordingly
    when (qrCodeGenerator) {

        // Generating State
        is QrGeneratorState.Generating -> {
            loaderUI()
        }

        // Generation of QR is Completed
        is QrGeneratorState.Completed -> {
            Image(
                modifier = modifier,
                bitmap = (qrCodeGenerator as QrGeneratorState.Completed).bitmap.asImageBitmap(),
                contentDescription = contentDescription
            )
        }
    }
}


/**
 * This function creates and returns the Qr Code Bitmap according to the given size and content
 * to be shown after scanning
 *
 * @param content This is the string to be shown after the QR Code is Scanned
 * @param size This is the size of the QR Scanner Code
 */
internal fun generateQr(
    content: String,
    size: Int
): Bitmap {

    // Qr Code Writer Variable to make a QR
    val qrCodeWriter = QRCodeWriter()

    // Encoding the Hints and configurations for the QR
    val encodeHints = mutableMapOf<EncodeHintType, Any?>()
        .apply {
            this[EncodeHintType.MARGIN] = 1
        }

    // This creates the Bitmap Matrix
    val bitmapMatrix = try {
        qrCodeWriter.encode(
            content, BarcodeFormat.QR_CODE,
            size, size, encodeHints
        )
    } catch (ex: WriterException) {
        null
    }

    // Matrix Dimensions
    val matrixWidth = bitmapMatrix?.width ?: size
    val matrixHeight = bitmapMatrix?.height ?: size

    // This is the Bitmap Variable which would be displayed as QR Code
    val newBitmap = Bitmap.createBitmap(
        bitmapMatrix?.width ?: size,
        bitmapMatrix?.height ?: size,
        Bitmap.Config.ARGB_8888,
    )

    // Populating the Bitmap Values in the UI
    for (x in 0 until matrixWidth) {
        for (y in 0 until matrixHeight) {
            val shouldColorPixel = bitmapMatrix?.get(x, y) ?: false
            val pixelColor = if (shouldColorPixel) Color.BLACK else Color.WHITE

            newBitmap.setPixel(x, y, pixelColor)
        }
    }

    return newBitmap
}
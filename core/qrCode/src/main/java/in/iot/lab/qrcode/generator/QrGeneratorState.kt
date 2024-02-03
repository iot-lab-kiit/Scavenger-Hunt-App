package `in`.iot.lab.qrcode.generator

import android.graphics.Bitmap


/**
 * This is the Sealed Class which defines all the possible states for Generating a QR Code
 *
 * @property Generating This defines that the code is being Generated at the moment
 * @property Completed This defines that the code is already done generating
 */
sealed interface QrGeneratorState {
    data object Generating : QrGeneratorState
    class Completed(val bitmap: Bitmap) : QrGeneratorState
}
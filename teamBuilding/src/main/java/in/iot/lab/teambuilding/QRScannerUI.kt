package `in`.iot.lab.teambuilding

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import `in`.iot.lab.qrcode.installer.ModuleInstaller
import `in`.iot.lab.qrcode.installer.ModuleInstallerState
import `in`.iot.lab.qrcode.scanner.QrCodeScanner
import `in`.iot.lab.qrcode.scanner.QrScannerState
import java.lang.Exception

@Composable
fun QRScannerUI() {
    val module = ModuleInstaller(LocalContext.current)
    var state by remember { mutableIntStateOf(0) }
    var exception by remember { mutableStateOf(Exception()) }
    var progress by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        module.checkAvailability {
            when (it) {
                is ModuleInstallerState.Idle -> {

                }

                is ModuleInstallerState.IsAvailable -> {
                    state = 1
                }

                is ModuleInstallerState.Downloading -> {
                    progress = it.progress
                }

                is ModuleInstallerState.InstallSuccessful -> {
                    state = 1
                }

                is ModuleInstallerState.Failure -> {
                    exception = it.exception
                    state = 0
                }
            }
        }
    }

    when (state) {
        0 -> {
            FailureUI(e = exception)
        }

        1 -> {
            SuccessUI()
        }

        2 -> {
            DownloadUI(progress = progress)
        }
    }
}

@Composable
fun SuccessUI() {
    Text(text = "Downloaded Successfully")
    val scanner = QrCodeScanner(LocalContext.current)
    scanner.startScanner(
        handleScannerState = {
            when (it) {
                is QrScannerState.Idle -> {

                }

                is QrScannerState.Running -> {

                }

                is QrScannerState.Cancelled -> {

                }

                is QrScannerState.Success -> {
                    val code = it.code
                }

                is QrScannerState.Failure -> {
                    val exception = it.exception
                }
            }
        }
    )
}


@Composable
fun FailureUI(e: Exception) {
    Text(text = e.message.toString())
}

@Composable
fun DownloadUI(progress: Int) {
    Text(text = "Progress : $progress")
}
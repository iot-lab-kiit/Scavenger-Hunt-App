package `in`.iot.lab.scavengerhunt.screen

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext


/**
 * This function is used to ask for the permission from the user for the Camera Access.
 *
 * @param onPermissionGranted This function is invoked when the user grants or denies
 * the Camera Permission.
 */
@Composable
fun PermissionScreen(
    onPermissionGranted: (Boolean) -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted)
                onPermissionGranted(true)
            else {
                onPermissionGranted(false)
                Toast.makeText(
                    context,
                    "You need to grant permission for running the App",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )

    // Launching the Permission Handler
    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.CAMERA)
    }
}
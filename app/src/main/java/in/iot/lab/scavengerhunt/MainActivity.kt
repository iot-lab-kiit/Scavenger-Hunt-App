package `in`.iot.lab.scavengerhunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.qrcode.installer.ModuleInstaller
import `in`.iot.lab.qrcode.installer.ModuleInstallerState
import `in`.iot.lab.scavengerhunt.navigation.MainNavGraph
import `in`.iot.lab.scavengerhunt.screen.SplashScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var moduleInstaller: ModuleInstaller

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ScavengerHuntTheme {

                LaunchedEffect(Unit) {
                    checkScannerModule()
                }

//                runBlocking {
//                    FirebaseAuth.getInstance().signOut()
//                }

                val installState = installState.collectAsState().value

                // Variable which says whether to show the splash screen or not
                var showSplash by remember { mutableStateOf(true) }

                // Checking whether to show splash screen or the App
                if (showSplash)
                    SplashScreen { showSplash = false }
                else if (
                    installState is ModuleInstallerState.IsAvailable ||
                    installState is ModuleInstallerState.InstallSuccessful
                ) {
                    // Main Nav Graph of the App
                    MainNavGraph(navHostController = rememberNavController())
                } else if (installState is ModuleInstallerState.Downloading)
                    LoadingTransition()
                else
                    ErrorDialog(
                        text = (installState as ModuleInstallerState.Failure)
                            .exception
                            .message
                            .toString(),
                        onCancel = {
                            finish()
                        },
                        onTryAgain = { checkScannerModule() }
                    )
            }
        }
    }


    /**
     * This variable is used to define the QR Scanner Download state
     */
    private val installState = MutableStateFlow<ModuleInstallerState>(ModuleInstallerState.Idle)


    /**
     * This function is used to  check if the scanner is already downloaded or not and if its not
     * downloaded then we start to download the Qr Code Scanner module.
     */
    private fun checkScannerModule() {

        installState.value = ModuleInstallerState.Downloading

        // Checking if the module is already downloaded
        moduleInstaller.checkAvailability {
            installState.value = it
        }
    }
}
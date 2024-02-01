package `in`.iot.lab.qrcode.installer

import android.content.Context
import com.google.android.gms.common.api.OptionalModuleApi
import com.google.android.gms.common.moduleinstall.InstallStatusListener
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_CANCELED
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_COMPLETED
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_FAILED
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import java.lang.Exception


/**
 * This class checks if the necessary modules are present or not they also handles downloading modules
 * which are not present and handling the Tracking of Download part too.
 *
 * @param context This is the context of the Activity from which the calls will be done
 */
class ModuleInstaller(context: Context) {

    /**
     * This is the client which helps to check the modules meta - data
     */
    private val moduleInstallClient = ModuleInstall.getClient(context)

    /**
     * This is the module which we need to download from play store
     */
    private val optionalModuleApi: OptionalModuleApi = GmsBarcodeScanning.getClient(context)


    /**
     * This function checks if the module is already installed.
     *
     * @param handleInstallState This state variable notifies the parent functions about state
     * changes in this Functionality
     */
    fun checkAvailability(handleInstallState: (ModuleInstallerState) -> Unit) {

        // This checks if the modules are already present or not
        moduleInstallClient
            .areModulesAvailable(optionalModuleApi)

            // Success Listener
            .addOnSuccessListener {
                if (it.areModulesAvailable())
                    handleInstallState(ModuleInstallerState.IsAvailable)
                else
                    requestModule(handleInstallState = handleInstallState)
            }

            // Failure Listener
            .addOnFailureListener {
                handleInstallState(ModuleInstallerState.Failure(exception = it))
            }
    }

    /**
     * This function configures the request and sends the request to download the module from the
     * play store.
     *
     * @param handleInstallState This state variable notifies the parent functions about
     * state changes in this Functionality
     */
    private fun requestModule(handleInstallState: (ModuleInstallerState) -> Unit) {

        // This is the Download Progress Listener which would be shown as UI
        val listener = ModuleDownloadProgressListener(handleInstallState = handleInstallState)

        // This is the configuration of the Module Install Request
        val moduleInstallRequest = ModuleInstallRequest
            .newBuilder()
            .addApi(optionalModuleApi)
            .setListener(listener)
            .build()

        // Requesting play store to download and install the module
        moduleInstallClient
            .installModules(moduleInstallRequest)

            // Success Listener
            .addOnSuccessListener {
                if (it.areModulesAlreadyInstalled())
                    handleInstallState(ModuleInstallerState.IsAvailable)
            }

            // Failure Listener
            .addOnFailureListener {
                handleInstallState(ModuleInstallerState.Failure(it))
            }
    }


    /**
     * This inner class will track the module download Progress when the module will be downloaded
     *
     * Note :- We can  use this to update the UI and show a progress Bar or something
     *
     * @param handleInstallState This state variable notifies the parent functions about state changes in this Functionality
     */
    inner class ModuleDownloadProgressListener(
        private val handleInstallState: (ModuleInstallerState) -> Unit
    ) : InstallStatusListener {

        // This function is called when there is a change in the progress of the module install
        override fun onInstallStatusUpdated(update: ModuleInstallStatusUpdate) {

            // Checking the Progress Info for showing the UI
            update.progressInfo?.let {

                // Calculating the progress Values
                val progress = ((it.bytesDownloaded / it.totalBytesToDownload) * 100).toInt()

                // Set Progress Bar
                handleInstallState(ModuleInstallerState.Downloading(progress))
            }

            // Checking the install state to decide the install updates
            when (update.installState) {

                // Success State
                STATE_COMPLETED -> handleInstallState(ModuleInstallerState.InstallSuccessful)

                // Cancelled / Failure State
                STATE_CANCELED -> handleInstallState(
                    ModuleInstallerState.Failure(Exception("Downloading Cancelled"))
                )

                // Failure State
                else -> handleInstallState(
                    ModuleInstallerState.Failure(Exception(update.errorCode.toString()))
                )
            }


            // This is done when the installation is either halted or done so it is no longer necessary
            if (isTerminateState(update.installState)) {
                moduleInstallClient.unregisterListener(this)
            }
        }

        /**
         * This function is called to check if the installation is halted or done or failed
         */
        private fun isTerminateState(@InstallState state: Int): Boolean {
            return state == STATE_CANCELED || state == STATE_COMPLETED || state == STATE_FAILED
        }
    }
}
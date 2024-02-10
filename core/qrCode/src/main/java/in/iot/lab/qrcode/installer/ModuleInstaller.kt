package `in`.iot.lab.qrcode.installer

import android.content.Context
import com.google.android.gms.common.api.OptionalModuleApi
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning



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
        moduleInstallClient.areModulesAvailable(optionalModuleApi)

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


        // This is the configuration of the Module Install Request
        val moduleInstallRequest = ModuleInstallRequest
            .newBuilder()
            .addApi(optionalModuleApi)
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
}
package `in`.iot.lab.qrcode.installer

import java.lang.Exception


/**
 * This sealed interface contains the states of the QR Scanner Module Download Phases
 * [ModuleInstaller] which would help to distinguish all the different states and help in handling
 * and passing data between them easier
 *
 * @property Idle This state defines that the [ModuleInstaller] is not installing any module
 * @property IsAvailable This state defines that the module is already installed in the device
 * @property Downloading This state defines that the module is being downloaded at the moment and
 * the progress of the download can also be passed and accessed.
 * @property InstallSuccessful This state defines that [ModuleInstaller] has finished installing the
 * Module successfully
 * @property Failure This state defines that [ModuleInstaller] has some internal error which needs to
 * be handled
 */
sealed interface ModuleInstallerState {

    data object Idle : ModuleInstallerState
    data object IsAvailable : ModuleInstallerState
    data object Downloading : ModuleInstallerState
    data object InstallSuccessful : ModuleInstallerState
    class Failure(val exception: Exception) : ModuleInstallerState
}
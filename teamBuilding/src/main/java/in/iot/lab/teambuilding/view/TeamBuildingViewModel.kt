package `in`.iot.lab.teambuilding.view

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.qrcode.installer.ModuleInstaller
import `in`.iot.lab.qrcode.installer.ModuleInstallerState
import `in`.iot.lab.qrcode.scanner.QrCodeScanner
import `in`.iot.lab.qrcode.scanner.QrScannerState
import `in`.iot.lab.teambuilding.view.events.TeamBuildingEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * This View model is used for the Team Building Api Calls.
 *
 * @param qrCodeScanner This is the [QrCodeScanner] object which is used to Scan QR Codes and get
 * the Team ID.
 * @param moduleInstaller This is the Module which check if the QR Code Scanner Module is downloaded
 * and downloads if its not previously downloaded.
 */
@HiltViewModel
class TeamBuildingViewModel @Inject constructor(
    private val qrCodeScanner: QrCodeScanner,
    private val moduleInstaller: ModuleInstaller
) : ViewModel() {


    /**
     * This variable is used to define the create team api call state
     */
    private val _createTeamApiState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val createTeamApiState = _createTeamApiState.asStateFlow()


    /**
     * This function creates the team and returns the Team UID to generate the QR Code
     */
    private fun createTeamApi(teamName: String) {

        // Checking if the api is already queued at the moment
        if (_createTeamApiState.value is UiState.Loading)
            return

        // Changing State to Loading
        _createTeamApiState.value = UiState.Loading

        viewModelScope.launch {

            // for testing
            delay(2000)

            // TODO :- Do the actual API call here

            _createTeamApiState.value = UiState.Success(data = "$teamName Uid is this this this")
        }
    }


    /**
     * This variable is used to define the QR Scanner Download state
     */
    private val _qrInstallerState =
        MutableStateFlow<ModuleInstallerState>(ModuleInstallerState.Idle)
    val qrInstallerState = _qrInstallerState.asStateFlow()


    /**
     * This function is used to  check if the scanner is already downloaded or not and if its not
     * downloaded then we start to download the [QrCodeScanner] module.
     */
    private fun checkScannerModule() {

        // Checking if the module is already downloaded
        moduleInstaller.checkAvailability {

            // updating the Module Installer State
            _qrInstallerState.value = it

            when (it) {

                // Is Already Installed
                is ModuleInstallerState.IsAvailable -> {
                    startScanner()
                }

                // Is Install Successful
                is ModuleInstallerState.InstallSuccessful -> {
                    startScanner()
                }

                else -> {
                    // Do Nothing
                }
            }
        }
    }


    /**
     * This variable defines the team joining api state
     */
    private val _teamJoiningApiState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val teamJoiningApiState = _teamJoiningApiState.asStateFlow()


    /**
     * This function starts the [QrCodeScanner] scanner and start to scan for QR Codes.
     */
    private fun startScanner() {
        qrCodeScanner.startScanner {
            when (it) {

                // User Cancelled The Scanner Scan
                is QrScannerState.Cancelled -> {
                    _teamJoiningApiState.value = UiState.Failed("User Cancelled QR Scanner")
                }

                // Scanner Scan is successful
                is QrScannerState.Success -> {
                    addTeamMember(it.code)
                }

                // Scanner scan is a failure
                is QrScannerState.Failure -> {
                    _teamJoiningApiState.value = UiState.Failed(it.exception.message.toString())
                }

                else -> {
                    // Do Nothing
                }
            }
        }
    }


    /**
     * This function calls the api to Add Team members to the Team
     */
    private fun addTeamMember(teamId: String) {

        // Checking if one request is already sent to the Server
        if (_teamJoiningApiState.value is UiState.Loading)
            return

        _teamJoiningApiState.value = UiState.Loading
        d("Team Building View Model", teamId)
    }


    /**
     * This function receives the events from the UI Layer and calls the Functions according to the
     * events received.
     *
     * @param event This is the [TeamBuildingEvent] sealed class object to pass the Events
     */
    fun uiListener(event: TeamBuildingEvent) {
        when (event) {
            is TeamBuildingEvent.CheckScannerAvailability -> {
                checkScannerModule()
            }

            is TeamBuildingEvent.CreateTeamApiCall -> {
                createTeamApi(teamName = event.teamName)
            }
        }
    }
}
package `in`.iot.lab.teambuilding.view.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkUtil.toUiState
import `in`.iot.lab.qrcode.installer.ModuleInstaller
import `in`.iot.lab.qrcode.installer.ModuleInstallerState
import `in`.iot.lab.qrcode.scanner.QrCodeScanner
import `in`.iot.lab.qrcode.scanner.QrScannerState
import `in`.iot.lab.teambuilding.data.repo.TeamBuildingRepo
import `in`.iot.lab.teambuilding.view.events.TeamBuildingEvent
import `in`.iot.lab.teambuilding.view.state.UserRegistrationState
import `in`.iot.lab.teambuilding.view.state.toUserRegistrationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


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
    @Named("production") private val repository: TeamBuildingRepo,
    firebase: FirebaseAuth,
    private val qrCodeScanner: QrCodeScanner,
    private val moduleInstaller: ModuleInstaller
) : ViewModel() {


    // Firebase UID
//    private val userFirebaseId = firebase.currentUser?.uid ?: ""
    private val userFirebaseId = "UID 07"
    private var userId = ""
    private var teamId: String? = null

    private val _teamData =
        MutableStateFlow<UserRegistrationState<RemoteTeam>>(UserRegistrationState.Idle)
    val teamData = _teamData.asStateFlow()


    private fun getUserRegistrationData() {

        if (_teamData.value is UserRegistrationState.Loading)
            return

        _teamData.value = UserRegistrationState.Loading

        viewModelScope.launch {
            val response = repository
                .getUserById(userFirebaseId)
                .toUiState()

            if (response is UiState.Success) {
                userId = response.data.id!!
                teamId = response.data.team

                if (response.data.team == null) {
                    _teamData.value = UserRegistrationState.NotRegistered(null)
                    return@launch
                }

                _teamData.value = repository
                    .getTeamById(teamId = teamId!!)
                    .toUiState()
                    .toUserRegistrationState()
            }
        }
    }


    /**
     * Team name is stored and used as a shared variable
     */
    private val _teamName = MutableStateFlow("")
    val teamName = _teamName.asStateFlow()


    /**
     * This function sets the team name variable
     */
    private fun setTeamName(teamName: String) {
        _teamName.value = teamName
    }


    /**
     * This variable is used to define the create team api call state
     */
    private val _teamDataState = MutableStateFlow<UiState<RemoteTeam>>(UiState.Idle)
    val teamDataState = _teamDataState.asStateFlow()


    /**
     * This function creates the team and returns the Team UID to generate the QR Code
     */
    private fun createTeamApi() {

        // Checking if the api is already queued at the moment
        if (_teamDataState.value is UiState.Loading)
            return

        // Changing State to Loading
        _teamDataState.value = UiState.Loading

        viewModelScope.launch {
            _teamDataState.value = repository
                .createTeam()
                .toUiState()
        }
    }


    /**
     * This function calls the api to Add Team members to the Team
     */
    private fun joinTeam(teamId: String) {

        // Checking if one request is already sent to the Server
        if (_teamDataState.value is UiState.Loading)
            return

        _teamDataState.value = UiState.Loading

        viewModelScope.launch {
            _teamDataState.value = repository
                .joinTeam()
                .toUiState()
        }
    }


    /**
     * This function calls the api to registers the Team in the backend.
     */
    private fun registerTeam() {

        if (_teamDataState.value is UiState.Loading)
            return

        _teamDataState.value = UiState.Loading

        viewModelScope.launch {
            _teamDataState.value = repository
                .registerTeam()
                .toUiState()
        }
    }


    /**
     * This function fetches the Team Data by using the Team Id
     */
    private fun getTeamById() {

        if (_teamDataState.value is UiState.Loading)
            return

        _teamDataState.value = UiState.Loading

        viewModelScope.launch {
            _teamDataState.value = repository
                .getTeamById("")
                .toUiState()
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
     * This function starts the [QrCodeScanner] scanner and start to scan for QR Codes.
     */
    private fun startScanner() {
        qrCodeScanner.startScanner {
            when (it) {

                // User Cancelled The Scanner Scan
                is QrScannerState.Cancelled -> {
                    _teamDataState.value = UiState.Failed("User Cancelled QR Scanner")
                }

                // Scanner Scan is successful
                is QrScannerState.Success -> {
                    joinTeam(it.code)
                }

                // Scanner scan is a failure
                is QrScannerState.Failure -> {
                    _teamDataState.value = UiState.Failed(it.exception.message.toString())
                }

                else -> {
                    // Do Nothing
                }
            }
        }
    }


    /**
     * This function receives the events from the UI Layer and calls the Functions according to the
     * events received.
     *
     * @param event This is the [TeamBuildingEvent] sealed class object to pass the Events
     */
    fun uiListener(event: TeamBuildingEvent) {
        when (event) {

            is TeamBuildingEvent.NetworkIO.GetUserRegistrationData -> {
                getUserRegistrationData()
            }

            is TeamBuildingEvent.Helper.SetTeamName -> {
                setTeamName(event.teamName)
            }

            is TeamBuildingEvent.ScannerIO.CheckScannerAvailability -> {
                checkScannerModule()
            }

            is TeamBuildingEvent.NetworkIO.CreateTeamApiCall -> {
                createTeamApi()
            }

            is TeamBuildingEvent.NetworkIO.RegisterTeamApiCall -> {
                registerTeam()
            }

            is TeamBuildingEvent.NetworkIO.GetTeamData -> {
                getTeamById()
            }
        }
    }
}
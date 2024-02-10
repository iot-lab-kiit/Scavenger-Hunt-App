package `in`.iot.lab.teambuilding.view.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.data.models.user.RemoteUser
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkConstants
import `in`.iot.lab.network.utils.NetworkUtil.toUiState
import `in`.iot.lab.qrcode.scanner.QrCodeScanner
import `in`.iot.lab.qrcode.scanner.QrScannerState
import `in`.iot.lab.teambuilding.data.model.CreateTeamBody
import `in`.iot.lab.teambuilding.data.model.UpdateTeamBody
import `in`.iot.lab.teambuilding.data.repo.TeamBuildingRepo
import `in`.iot.lab.teambuilding.view.events.TeamBuildingEvent
import `in`.iot.lab.teambuilding.view.state.UserRegistrationState
import `in`.iot.lab.teambuilding.view.state.toUserRegistrationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * This View model is used for the Team Building Api Calls.
 *
 * @param qrCodeScanner This is the [QrCodeScanner] object which is used to Scan QR Codes and get
 * the Team ID.
 */
@HiltViewModel
class TeamBuildingViewModel @Inject constructor(
    private val repository: TeamBuildingRepo,
    firebase: FirebaseAuth,
    private val qrCodeScanner: QrCodeScanner
) : ViewModel() {


    // Firebase UID
//    private val userFirebaseId = firebase.currentUser?.uid ?: ""
    private val userFirebaseId = NetworkConstants.USER_UID
    private var userId = ""
    private var teamId: String? = null


    /**
     * This variable checks the Registration state of the user which is then used to decide if the
     * user is registered or in a team or is neither of them
     */
    private val _registrationState =
        MutableStateFlow<UserRegistrationState>(UserRegistrationState.Idle)
    val registrationState = _registrationState.asStateFlow()


    /**
     * This variable is used to store the user's data.
     */
    private val _userData = MutableStateFlow(RemoteUser())
    val userData = _userData.asStateFlow()


    /**
     * This is the team Data which can be used in the overall App i.e in all the screens of the
     * Team Building Screens
     */
    private val _teamData = MutableStateFlow<UiState<RemoteTeam>>(UiState.Idle)
    val teamData = _teamData.asStateFlow()


    /**
     * This function is used to get the user's registration details
     */
    private fun getUserRegistrationData() {

        // Checking if already an api call is done
        if (_registrationState.value is UserRegistrationState.Loading)
            return

        // Toggling the Loading State for the Loading UI
        _registrationState.value = UserRegistrationState.Loading

        viewModelScope.launch {

            // Fetching the User's Data to get the teamId from the User DB
            val response = repository
                .getUserById(userFirebaseId)
                .toUiState()

            // Checking if the Api call is successful or not
            if (response is UiState.Success) {

                // Setting the User Id and the Team Id
                userId = response.data.id!!
                _userData.value = response.data
                teamId = response.data.team

                // Checking if the Team id is null
                if (teamId == null) {

                    // Null means that the user is not in a team currently (Not Registered)
                    _registrationState.value = UserRegistrationState.NotRegistered
                    return@launch
                }

                // Fetching the Team Data if the Team Id is not null
                val teamDataResponse = repository
                    .getTeamById(userFirebaseId)
                    .toUiState()

                if (response !is UiState.Loading)
                    _teamData.value = teamDataResponse

                // Setting the Registration State accordingly
                _registrationState.value = _teamData.value.toUserRegistrationState()
            } else if (response is UiState.Failed)
                _registrationState.value = UserRegistrationState.Error(response.message)
        }
    }


    /**
     * This function fetches the Team Data by using the Team Id
     */
    private fun getTeamById() {

        // Checking if the Api state is already loading
        if (_teamData.value is UiState.Loading)
            return

        // Setting the Loading State
        _teamData.value = UiState.Loading

        viewModelScope.launch {
            _teamData.value = repository
                .getTeamById(userFirebaseId)
                .toUiState()
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
     * This function creates the team and returns the Team UID to generate the QR Code
     */
    private fun createTeamApi() {

        // Checking if the api is already queued at the moment
        if (_teamData.value is UiState.Loading)
            return

        // Changing State to Loading
        _teamData.value = UiState.Loading

        viewModelScope.launch {
            _teamData.value = repository
                .createTeam(
                    CreateTeamBody(
                        teamName = _teamName.value,
                        teamLead = userId,
                        teamMembers = listOf(userId)
                    )
                )
                .toUiState()

            // Updating Team Id after fetching.
            if (_teamData.value is UiState.Success)
                teamId = (_teamData.value as UiState.Success<RemoteTeam>).data.id
        }
    }


    /**
     * This function calls the api to Add Team members to the Team
     */
    private fun joinTeam(joinTeamId: String) {

        // Checking if one request is already sent to the Server
        if (_teamData.value is UiState.Loading)
            return

        _teamData.value = UiState.Loading

        viewModelScope.launch {
            _teamData.value = repository
                .joinTeam(
                    updateTeam = UpdateTeamBody(userId = userFirebaseId),
                    teamId = joinTeamId
                )
                .toUiState()

            // Updating Team Id after fetching.
            if (_teamData.value is UiState.Success)
                teamId = joinTeamId
        }
    }

    private var tempTeamData: UiState<RemoteTeam> = UiState.Idle


    /**
     * This function calls the api to registers the Team in the backend.
     */
    private fun registerTeam() {

        tempTeamData = _teamData.value

        if (_teamData.value is UiState.Loading)
            return

        _teamData.value = UiState.Loading

        viewModelScope.launch {
            _teamData.value = repository
                .registerTeam(
                    updateTeam = UpdateTeamBody
                        (
                        userId = userFirebaseId,
                        isRegistered = true
                    ),
                    teamId = teamId!!
                )
                .toUiState()
        }
    }


    private fun onCancelInRegisterScreenClick() {
        _teamData.value = tempTeamData
    }


    /**
     * This function starts the [QrCodeScanner] scanner and start to scan for QR Codes.
     */
    private fun startScanner() {
        qrCodeScanner.startScanner {
            when (it) {

                // User Cancelled The Scanner Scan
                is QrScannerState.Cancelled -> {
                    _teamData.value = UiState.Failed("User Cancelled QR Scanner")
                }

                // Scanner Scan is successful
                is QrScannerState.Success -> {
                    joinTeam(it.code)
                }

                // Scanner scan is a failure
                is QrScannerState.Failure -> {
                    _teamData.value = UiState.Failed(it.exception.message.toString())
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
                startScanner()
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

            is TeamBuildingEvent.Helper.OnClickInRegisterScreen -> {
                onCancelInRegisterScreenClick()
            }
        }
    }
}
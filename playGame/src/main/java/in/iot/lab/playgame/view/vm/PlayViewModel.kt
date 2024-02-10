package `in`.iot.lab.playgame.view.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.network.data.models.hint.RemoteHint
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkUtil.toUiState
import `in`.iot.lab.network.utils.await
import `in`.iot.lab.playgame.data.model.UpdatePointRequest
import `in`.iot.lab.playgame.data.repo.PlayRepo
import `in`.iot.lab.playgame.view.event.PlayGameEvent
import `in`.iot.lab.qrcode.scanner.QrCodeScanner
import `in`.iot.lab.qrcode.scanner.QrScannerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlayViewModel @Inject constructor(
    private val qrCodeScanner: QrCodeScanner,
    private val repository: PlayRepo,
    private val auth: FirebaseAuth
) : ViewModel() {

    private var teamId = ""

    private var userUid: String? = auth.currentUser?.uid

    /**
     * This variable is used to store the teamData.
     */
    private val _hintData = MutableStateFlow<UiState<RemoteHint>>(UiState.Idle)
    val hintData = _hintData.asStateFlow()


    /**
     * This variable is used to store the team data.
     */
    private val _teamData = MutableStateFlow<UiState<RemoteTeam>>(UiState.Idle)
    val teamData = _teamData.asStateFlow()

    private var hintId = ""


    init {
        getTeamByUserUid()
    }


    /**
     * This function fetches the Team Data from the Server.
     */
    private fun getTeamByUserUid() {

        if (_teamData.value is UiState.Loading)
            return

        _teamData.value = UiState.Loading

        if (userUid == null) {
            _teamData.value = UiState.Failed("Data Not Found! Please restart the App Once.")
            return
        }

        viewModelScope.launch {

            val token = auth.currentUser!!.getIdToken(false).await().token
            val bearerToken = "Bearer $token"

            _teamData.value = repository
                .getTeamById(userUid!!, bearerToken)
                .toUiState()

            if (_teamData.value is UiState.Success)
                teamId = (_teamData.value as UiState.Success<RemoteTeam>).data.id ?: ""

        }
    }


    /**
     * This function is called after the scanner scans the hint. It updates the points in the
     * database for the correct hint.
     */
    private fun updatePoints(hintId: String) {

        this.hintId = hintId

        if (_hintData.value is UiState.Loading)
            return

        _hintData.value = UiState.Loading

        viewModelScope.launch {

            val token = auth.currentUser!!.getIdToken(false).await().token
            val bearerToken = "Bearer $token"

            _hintData.value = repository
                .updateHints(
                    teamId = teamId,
                    updatePointRequest = UpdatePointRequest(
                        score = 100,
                        hintId = hintId
                    ),
                    token = bearerToken
                ).toUiState()
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
                    _hintData.value = UiState.Failed("User Cancelled the Scanner!")
                }

                // Scanner Scan is successful
                is QrScannerState.Success -> {
                    updatePoints(hintId = it.code)
                }

                // Scanner scan is a failure
                is QrScannerState.Failure -> {
                    _hintData.value = UiState.Failed(it.exception.message.toString())
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
     * @param event This is the [PlayGameEvent] sealed class object to pass the Events
     */
    fun uiListener(event: PlayGameEvent) {
        when (event) {

            is PlayGameEvent.ScannerIO.CheckScannerAvailability -> {
                startScanner()
            }

            is PlayGameEvent.NetworkIO.GetTeamData -> {
                getTeamByUserUid()
            }

            is PlayGameEvent.NetworkIO.GetHintDetails -> {
                updatePoints(hintId)
            }

            is PlayGameEvent.Helper.ResetScanner -> {
                startScanner()
            }
        }
    }
}
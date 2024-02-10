package `in`.iot.lab.playgame.view.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkUtil.toUiState
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
    private val repository: PlayRepo
) : ViewModel() {


    /**
     * This variable is used to store the teamData.
     */
    private val _teamData = MutableStateFlow<UiState<RemoteTeam>>(UiState.Idle)
    val teamData = _teamData.asStateFlow()


    /**
     * This function is called after the scanner scans the hint. It updates the points in the
     * database for the correct hint.
     */
    private fun updatePoints(hintId: String = "65c71e2c8cdd1013220429d5") {

        if (_teamData.value is UiState.Loading)
            return

        _teamData.value = UiState.Loading

        viewModelScope.launch {
            _teamData.value = repository
                .updateHints(
                    teamId = "65c71e7b8cdd101322042a76",
                    updatePointRequest = UpdatePointRequest(
                        score = 100,
                        hintId = hintId
                    )
                ).toUiState()
        }

    }


    private val _scannerState = MutableStateFlow<QrScannerState>(QrScannerState.Idle)
    val scannerState = _scannerState.asStateFlow()


    /**
     * This function starts the [QrCodeScanner] scanner and start to scan for QR Codes.
     */
    private fun startScanner() {
        qrCodeScanner.startScanner {

            _scannerState.value = it

            when (it) {

                // User Cancelled The Scanner Scan
                is QrScannerState.Cancelled -> {
                    TODO("Handle Scanner Cancelled State")
                }

                // Scanner Scan is successful
                is QrScannerState.Success -> {
                    updatePoints(hintId = it.code)
                }

                // Scanner scan is a failure
                is QrScannerState.Failure -> {
                    TODO("Handle Scanner Failure State")
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
//                startScanner()
                updatePoints()
            }
        }
    }
}
package `in`.iot.lab.playgame.view.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.network.data.models.hint.RemoteHint
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

    private var teamId = ""


    /**
     * This variable is used to store the teamData.
     */
    private val _hintData = MutableStateFlow<UiState<RemoteHint>>(UiState.Idle)
    val hintData = _hintData.asStateFlow()


    /**
     * This function is called after the scanner scans the hint. It updates the points in the
     * database for the correct hint.
     */
    private fun updatePoints(hintId: String) {

        if (_hintData.value is UiState.Loading)
            return

        _hintData.value = UiState.Loading

        viewModelScope.launch {
            _hintData.value = repository
                .updateHints(
                    teamId = teamId,
                    updatePointRequest = UpdatePointRequest(
                        score = 100,
                        hintId = hintId
                    )
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
        }
    }
}
package `in`.iot.lab.playgame.view.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.network.data.models.hint.RemoteHint
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkUtil.toUiState
import `in`.iot.lab.network.utils.await
import `in`.iot.lab.playgame.data.model.UpdatePointRequest
import `in`.iot.lab.playgame.data.repo.PlayRepo
import `in`.iot.lab.playgame.view.event.PlayGameEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlayViewModel @Inject constructor(
    private val repository: PlayRepo,
    private val auth: FirebaseAuth
) : ViewModel() {

    private var userUid: String? = auth.currentUser?.uid

    /**
     * This variable is used to store the teamData.
     */
    private val _hintData = MutableStateFlow<UiState<RemoteHint>>(UiState.Idle)
    val hintData = _hintData.asStateFlow()

    private var hintId = ""


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

            val teamData = repository
                .getTeamById(userUid!!, bearerToken)
                .toUiState()

            if (teamData is UiState.Success) {
                _hintData.value = repository
                    .updateHints(
                        teamId = teamData.data.id!!,
                        updatePointRequest = UpdatePointRequest(
                            score = 0,
                            hintId = hintId
                        ),
                        token = bearerToken
                    ).toUiState()
            } else if (teamData is UiState.Failed)
                _hintData.value = UiState.Failed(teamData.message)
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

            is PlayGameEvent.ScannerIO.ResetScanner -> {
                _hintData.value = UiState.Idle
            }

            is PlayGameEvent.NetworkIO.UpdatePoints -> {
                updatePoints(event.hintId)
            }

            is PlayGameEvent.ScannerIO.ScannerFailure -> {
                _hintData.value =
                    UiState.Failed("Qr scanner failed to scan! Please restart and try again.")
            }
        }
    }
}
package `in`.iot.lab.dashboard.ui.screen.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.dashboard.data.repository.DashboardRepository
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkUtil.toUiState
import `in`.iot.lab.network.utils.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TeamScreenViewModel @Inject constructor(
    private val repository: DashboardRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    // User's Firebase UID
    private val userUid = auth.currentUser?.uid

    // Team Data
    private val _teamData = MutableStateFlow<UiState<RemoteTeam>>(UiState.Idle)
    val teamData = _teamData.asStateFlow()

//    init {
//        getTeamByUserUid()
//    }


    /**
     * This function fetches the Team Data from the Server.
     */
    fun getTeamByUserUid() {

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
                .getCurrentUserTeamByUserUid(userUid, bearerToken)
                .toUiState()
        }
    }
}
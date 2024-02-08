package `in`.iot.lab.leaderboard.view.vm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.leaderboard.view.event.LeaderBoardEvent
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class LeaderBoardViewModel @Inject constructor() : ViewModel() {


    /**
     * This variable is used to store the leader board team list data to be shown in the UI
     */
    private val _teamList = MutableStateFlow<UiState<List<RemoteTeam>>>(UiState.Idle)
    val teamList = _teamList.asStateFlow()


    /**
     * This function is used to fetch the leader board team list data from the backend server.
     */
    private fun getLeaderBoardData() {
        _teamList.value = UiState.Success(
            listOf(
                RemoteTeam(teamName = "Team 01"),
                RemoteTeam(teamName = "Team 02"),
                RemoteTeam(teamName = "Team 03"),
                RemoteTeam(teamName = "Team 04"),
                RemoteTeam(teamName = "Team 05"),
                RemoteTeam(teamName = "Team 06"),
                RemoteTeam(teamName = "Team 07"),
                RemoteTeam(teamName = "Team 08"),
                RemoteTeam(teamName = "Team 09"),
                RemoteTeam(teamName = "Team 10"),
                RemoteTeam(teamName = "Team 01"),
                RemoteTeam(teamName = "Team 02"),
                RemoteTeam(teamName = "Team 03"),
                RemoteTeam(teamName = "Team 04"),
                RemoteTeam(teamName = "Team 05"),
                RemoteTeam(teamName = "Team 06"),
                RemoteTeam(teamName = "Team 07"),
                RemoteTeam(teamName = "Team 08"),
                RemoteTeam(teamName = "Team 09"),
                RemoteTeam(teamName = "Team 10"),
                RemoteTeam(teamName = "Team 01"),
                RemoteTeam(teamName = "Team 02"),
                RemoteTeam(teamName = "Team 03"),
                RemoteTeam(teamName = "Team 04"),
                RemoteTeam(teamName = "Team 05"),
                RemoteTeam(teamName = "Team 06"),
                RemoteTeam(teamName = "Team 07"),
                RemoteTeam(teamName = "Team 08"),
                RemoteTeam(teamName = "Team 09"),
                RemoteTeam(teamName = "Team 10"),
                RemoteTeam(teamName = "Team 01"),
                RemoteTeam(teamName = "Team 02"),
                RemoteTeam(teamName = "Team 03"),
                RemoteTeam(teamName = "Team 04"),
                RemoteTeam(teamName = "Team 05"),
                RemoteTeam(teamName = "Team 06"),
                RemoteTeam(teamName = "Team 07"),
                RemoteTeam(teamName = "Team 08"),
                RemoteTeam(teamName = "Team 09"),
                RemoteTeam(teamName = "Team 10"),
                RemoteTeam(teamName = "Team 01"),
                RemoteTeam(teamName = "Team 02"),
                RemoteTeam(teamName = "Team 03"),
                RemoteTeam(teamName = "Team 04"),
                RemoteTeam(teamName = "Team 05"),
                RemoteTeam(teamName = "Team 06"),
                RemoteTeam(teamName = "Team 07"),
                RemoteTeam(teamName = "Team 08"),
                RemoteTeam(teamName = "Team 09"),
                RemoteTeam(teamName = "Team 10")
            )
        )
    }


    /**
     * This function is used to receive the events from the UI layer to the View Model layer and
     * it decides what to do for each event.
     *
     * @param event This is the event.
     */
    fun uiListener(event: LeaderBoardEvent) {

        // Checking event type
        when (event) {
            is LeaderBoardEvent.GetLeaderBoard -> {
                getLeaderBoardData()
            }
        }
    }
}
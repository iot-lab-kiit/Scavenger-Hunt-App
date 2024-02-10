package `in`.iot.lab.dashboard.ui.screen.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.dashboard.data.repository.DashboardRepository
import `in`.iot.lab.network.data.models.hint.RemoteHint
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.data.models.user.RemoteUser
import `in`.iot.lab.network.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamScreenViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _teamData = MutableStateFlow<UiState<RemoteTeam>>(UiState.Idle)
    val teamData = _teamData.asStateFlow()

    init {
        getTeamById()
    }

    fun getTeamById(userId: String = "") {

        if (_teamData.value is UiState.Loading)
            return

        _teamData.value = UiState.Loading

        viewModelScope.launch {
//            _teamData.value = repository
//                .getTeamById(userId)
//                .toUiState()

            _teamData.value = UiState.Success(fakeTeam)
        }
    }
}

// TODO: Only for testing
private val fakeTeam = RemoteTeam(
    teamName = "Team 1",
    teamLead = RemoteUser(name = "Member 1"),
    teamMembers = listOf(
        RemoteUser(name = "Member 1", isLead = true),
        RemoteUser(name = "Member 2"),
        RemoteUser(name = "Member 3"),
        RemoteUser(name = "Member 4"),
        RemoteUser(name = "Member 5")
    ),
    mainQuest = listOf(
        RemoteHint(
            id = "1",
            answer = "This is a hint",
            campus = 1,
            question = "What is this?",
            type = "main"
        ),
        RemoteHint(
            id = "2",
            answer = "This is a hint",
            campus = 1,
            question = "What is this?",
            type = "main"
        ),
        RemoteHint(
            id = "3",
            answer = "This is a hint",
            campus = 1,
            question = "What is this?",
            type = "main"
        ),
        RemoteHint(
            id = "4",
            answer = "This is a hint",
            campus = 1,
            question = "What is this?",
            type = "main"
        ),
        RemoteHint(
            id = "5",
            answer = "This is a hint",
            campus = 1,
            question = "What is this?",
            type = "main"
        )
    )
)
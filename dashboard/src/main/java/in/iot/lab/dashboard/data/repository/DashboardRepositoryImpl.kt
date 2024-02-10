package `in`.iot.lab.dashboard.data.repository


import `in`.iot.lab.dashboard.data.remote.TeamApiService
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val teamApiService: TeamApiService
) : DashboardRepository {

    override suspend fun getCurrentUserTeamByUserUid(
        userUid: String,
        token: String
    ): ResponseState<RemoteTeam> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                teamApiService.getTeamByUserUid(
                    token = token,
                    id = userUid
                )
            }
        }
    }
}
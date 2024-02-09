package `in`.iot.lab.dashboard.data.repository

import com.google.firebase.auth.FirebaseAuth
import `in`.iot.lab.dashboard.data.remote.TeamApiService
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val teamApiService: TeamApiService,
    private val auth: FirebaseAuth
) : DashboardRepository {
    override suspend fun getCurrentUserTeam(): ResponseState<RemoteTeam> {
        return withContext(Dispatchers.IO) {
            NetworkUtil.getResponseState {
//                if (auth.currentUser == null) {
//                    ResponseState.UserNotAuthorized
//                }
//                val userId = auth.currentUser!!.uid
                val result = teamApiService.getTeamByUserId("UID 04")
                result
            }
        }
    }
}
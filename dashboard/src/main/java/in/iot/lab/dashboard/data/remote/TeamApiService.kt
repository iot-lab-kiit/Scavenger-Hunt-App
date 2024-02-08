package `in`.iot.lab.dashboard.data.remote

import `in`.iot.lab.network.data.models.Response
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.utils.NetworkConstants
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamApiService {
    @GET(NetworkConstants.GET_TEAM_BY_ID_ENDPOINT)
    suspend fun getTeamById(@Path("id") id: String): Response<RemoteTeam>
}
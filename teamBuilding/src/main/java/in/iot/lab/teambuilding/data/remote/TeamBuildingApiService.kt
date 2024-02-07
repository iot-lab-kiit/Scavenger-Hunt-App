package `in`.iot.lab.teambuilding.data.remote

import `in`.iot.lab.network.data.models.Response
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.data.models.user.RemoteUser
import `in`.iot.lab.network.utils.NetworkConstants
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * This interface is the functions the Retrofit api calls can have and this functions are
 * implemented by retrofit themselves.
 * We use these functions to make api calls to server.
 */
interface TeamBuildingApiService {

    /**
     * This function returns the [RemoteUser] data by taking the user's id.
     */
    @GET(NetworkConstants.GET_USER_BY_ID_ENDPOINT)
    suspend fun getUserById(
        @Path("id") userId: String
    ): Response<RemoteUser>


    /**
     * This function returns the [RemoteTeam] data object by taking the team id.
     */
    @GET(NetworkConstants.GET_TEAM_BY_ID_ENDPOINT)
    suspend fun getTeamById(
        @Path("id") teamId: String
    ): Response<RemoteTeam>
}
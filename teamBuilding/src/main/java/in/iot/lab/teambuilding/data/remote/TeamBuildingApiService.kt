package `in`.iot.lab.teambuilding.data.remote

import `in`.iot.lab.network.data.models.Response
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.data.models.user.RemoteUser
import `in`.iot.lab.network.utils.NetworkConstants
import `in`.iot.lab.teambuilding.data.model.CreateTeamBody
import `in`.iot.lab.teambuilding.data.model.UpdateTeamBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
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
        @Header("Authorization") token: String,
        @Path("id") userId: String
    ): Response<RemoteUser>


    /**
     * This function returns the [RemoteTeam] data object by taking the team id.
     */
    @GET(NetworkConstants.GET_TEAM_BY_USER_ID)
    suspend fun getTeamById(
        @Header("Authorization") token: String,
        @Path("id") teamId: String
    ): Response<RemoteTeam>


    /**
     * This function creates a team [RemoteTeam] in the backend and returns the Team Created.
     */
    @POST(NetworkConstants.CREATE_TEAM_ENDPOINT)
    suspend fun createTeam(
        @Header("Authorization") token: String,
        @Body teamData: CreateTeamBody
    ): Response<RemoteTeam>


    /**
     * This function updates the Team details [RemoteTeam] in the backend and returns the Team.
     *
     * @param teamId This takes the id of the Team
     * @param updateTeam This is the body to be passed to the Api call.
     */
    @PATCH(NetworkConstants.UPDATE_TEAM_ENDPOINT)
    suspend fun updateTeam(
        @Header("Authorization") token: String,
        @Path("id") teamId: String,
        @Body updateTeam: UpdateTeamBody
    ): Response<RemoteTeam>
}
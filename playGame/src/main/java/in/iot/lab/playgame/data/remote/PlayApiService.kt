package `in`.iot.lab.playgame.data.remote

import `in`.iot.lab.network.data.models.Response
import `in`.iot.lab.network.data.models.hint.RemoteHint
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.utils.NetworkConstants
import `in`.iot.lab.playgame.data.model.UpdatePointRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface PlayApiService {


    /**
     * This endpoint patch request is used to update the points of the team.
     *
     * @param teamId This is the id of the team.
     * @param updatePointRequest This is the updated points Request body [UpdatePointRequest] where
     * we are sending the hintId and the score to the Backend.
     */
    @PATCH(NetworkConstants.UPDATE_POINTS_ENDPOINT)
    suspend fun updatePoints(
        @Path("id") teamId: String,
        @Body updatePointRequest: UpdatePointRequest
    ): Response<RemoteHint>

    /**
     * This function returns the [RemoteTeam] data object by taking the team id.
     */
    @GET(NetworkConstants.GET_TEAM_BY_USER_ID)
    suspend fun getTeamById(
        @Path("id") teamId: String
    ): Response<RemoteTeam>

}
package `in`.iot.lab.leaderboard.data.remote

import `in`.iot.lab.network.data.models.Response
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.utils.NetworkConstants
import retrofit2.http.GET


/**
 * This interface is implemented by Retrofit which can then be used to get data from the server
 * and make calls to fetch the leader board data.
 */
interface LeaderBoardApiService {

    /**
     * This function sends a request to the user to send the Leader Board data which is a list
     * of teams [RemoteTeam].
     */
    @GET(NetworkConstants.GET_LEADERBOARD_ENDPOINT)
    suspend fun getLeaderBoardData(): Response<List<RemoteTeam>>

}
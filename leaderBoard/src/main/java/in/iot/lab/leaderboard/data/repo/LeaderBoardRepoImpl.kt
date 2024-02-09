package `in`.iot.lab.leaderboard.data.repo

import `in`.iot.lab.leaderboard.data.remote.LeaderBoardApiService
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LeaderBoardRepoImpl @Inject constructor(
    private val apiService: LeaderBoardApiService
) : LeaderBoardRepo {


    /**
     * This function is used to fetch the leader board team list [RemoteTeam].
     */
    override suspend fun getLeaderBoard(): ResponseState<List<RemoteTeam>> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.getLeaderBoardData()
            }
        }
    }

}
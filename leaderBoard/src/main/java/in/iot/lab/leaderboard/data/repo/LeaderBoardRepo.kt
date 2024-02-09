package `in`.iot.lab.leaderboard.data.repo

import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.ResponseState

interface LeaderBoardRepo {

    suspend fun getLeaderBoard(): ResponseState<List<RemoteTeam>>

}
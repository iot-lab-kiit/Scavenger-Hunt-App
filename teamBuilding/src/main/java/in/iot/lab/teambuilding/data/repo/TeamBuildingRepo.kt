package `in`.iot.lab.teambuilding.data.repo

import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.data.models.user.RemoteUser
import `in`.iot.lab.network.state.ResponseState

interface TeamBuildingRepo {

    suspend fun getUserById(userId: String): ResponseState<RemoteUser>
    suspend fun getTeamById(teamId: String): ResponseState<RemoteTeam>

}
package `in`.iot.lab.teambuilding.data.repo

import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.data.models.user.RemoteUser
import `in`.iot.lab.network.state.ResponseState

interface TeamBuildingRepo {

    suspend fun getUserById(userId: String): ResponseState<RemoteUser>
    suspend fun createTeam(): ResponseState<RemoteTeam>
    suspend fun joinTeam(): ResponseState<RemoteTeam>
    suspend fun registerTeam(): ResponseState<RemoteTeam>
    suspend fun getTeamById(): ResponseState<RemoteTeam>
}
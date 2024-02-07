package `in`.iot.lab.teambuilding.data.repo

import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.data.models.user.RemoteUser
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.teambuilding.data.model.CreateTeamBody

interface TeamBuildingRepo {

    suspend fun getUserById(userId: String): ResponseState<RemoteUser>
    suspend fun createTeam(teamData : CreateTeamBody): ResponseState<RemoteTeam>
    suspend fun joinTeam(): ResponseState<RemoteTeam>
    suspend fun registerTeam(): ResponseState<RemoteTeam>
    suspend fun getTeamById(teamId : String): ResponseState<RemoteTeam>
}
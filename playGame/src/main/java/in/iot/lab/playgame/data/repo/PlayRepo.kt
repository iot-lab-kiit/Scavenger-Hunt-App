package `in`.iot.lab.playgame.data.repo

import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.playgame.data.model.UpdatePointRequest

interface PlayRepo {

    suspend fun updateHints(
        teamId: String,
        updatePointRequest: UpdatePointRequest
    ): ResponseState<RemoteTeam>
}
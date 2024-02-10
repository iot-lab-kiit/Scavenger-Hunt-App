package `in`.iot.lab.playgame.data.repo

import `in`.iot.lab.network.data.models.hint.RemoteHint
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getResponseState
import `in`.iot.lab.playgame.data.model.UpdatePointRequest
import `in`.iot.lab.playgame.data.remote.PlayApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PlayRepoImpl @Inject constructor(
    private val apiService: PlayApiService
) : PlayRepo {


    /**
     * This function updates the data when the hint scanned by the user is correct otherwise it
     * shows wrong Hint Scanned.
     */
    override suspend fun updateHints(
        teamId: String,
        updatePointRequest: UpdatePointRequest
    ): ResponseState<RemoteHint> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.updatePoints(teamId = teamId, updatePointRequest = updatePointRequest)
            }
        }
    }

}
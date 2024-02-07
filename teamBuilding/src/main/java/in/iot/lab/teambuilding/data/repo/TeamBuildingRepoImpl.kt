package `in`.iot.lab.teambuilding.data.repo

import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.data.models.user.RemoteUser
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getResponseState
import `in`.iot.lab.teambuilding.data.model.CreateTeamBody
import `in`.iot.lab.teambuilding.data.model.UpdateTeamBody
import `in`.iot.lab.teambuilding.data.remote.TeamBuildingApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Team Building Features Repo which is responsible for fetching data for the View Model.
 *
 * @param apiService Retrofit Instance enabling the repo layer to make api calls.
 */
class TeamBuildingRepoImpl @Inject constructor(
    private val apiService: TeamBuildingApiService
) : TeamBuildingRepo {


    /**
     * This function fetches the [RemoteUser] object by taking the user's UID
     */
    override suspend fun getUserById(userId: String): ResponseState<RemoteUser> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.getUserById(userId = userId)
            }
        }
    }


    /**
     * This function creates a team using [CreateTeamBody] object and returns the [RemoteTeam]
     * object which is created by the backend.
     */
    override suspend fun createTeam(teamData: CreateTeamBody): ResponseState<RemoteTeam> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.createTeam(teamData)
            }
        }
    }

    override suspend fun joinTeam(
        updateTeam: UpdateTeamBody,
        teamId: String
    ): ResponseState<RemoteTeam> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.updateTeam(teamId, updateTeam)
            }
        }
    }

    override suspend fun registerTeam(): ResponseState<RemoteTeam> {
        TODO("Not yet implemented")
    }


    /**
     * This function fetches the team data [RemoteTeam] by using the team's Id.
     */
    override suspend fun getTeamById(teamId: String): ResponseState<RemoteTeam> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.getTeamById(teamId)
            }
        }
    }
}
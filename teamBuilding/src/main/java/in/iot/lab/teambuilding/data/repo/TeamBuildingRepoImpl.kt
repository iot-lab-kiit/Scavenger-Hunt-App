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
    override suspend fun getUserById(
        userId: String,
        token: String
    ): ResponseState<RemoteUser> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.getUserById(
                    userId = userId,
                    token = token,
                )
            }
        }
    }


    /**
     * This function creates a team using [CreateTeamBody] object and returns the [RemoteTeam]
     * object which is created by the backend.
     */
    override suspend fun createTeam(
        teamData: CreateTeamBody,
        token: String
    ): ResponseState<RemoteTeam> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.createTeam(
                    teamData = teamData,
                    token = token,
                )
            }
        }
    }


    /**
     * This function updates the team using the [UpdateTeamBody] object and returns the updated team
     * [RemoteTeam] object
     */
    override suspend fun joinTeam(
        updateTeam: UpdateTeamBody,
        teamId: String,
        token: String
    ): ResponseState<RemoteTeam> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.updateTeam(
                    teamId = teamId,
                    updateTeam = updateTeam,
                    token = token,
                )
            }
        }
    }


    /**
     * This function is used to register the Team and it returns the [RemoteTeam] object from the
     * backend.
     */
    override suspend fun registerTeam(
        updateTeam: UpdateTeamBody,
        teamId: String,
        token: String
    ): ResponseState<RemoteTeam> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.updateTeam(
                    token = token,
                    teamId = teamId,
                    updateTeam = updateTeam
                )
            }
        }
    }


    /**
     * This function fetches the team data [RemoteTeam] by using the team's Id.
     */
    override suspend fun getTeamById(
        teamId: String,
        token: String
    ): ResponseState<RemoteTeam> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.getTeamById(
                    token = token,
                    teamId = teamId
                )
            }
        }
    }
}
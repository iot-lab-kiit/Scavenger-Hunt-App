package `in`.iot.lab.teambuilding.data.repo


import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.data.models.user.RemoteUser
import `in`.iot.lab.network.state.ResponseState
import kotlinx.coroutines.delay
import javax.inject.Inject

class TeamBuildingRepoDummy @Inject constructor() : TeamBuildingRepo {

    override suspend fun getUserById(userId: String): ResponseState<RemoteUser> {

        // Delay for Testing Loading State
        delay(2000)

        return when (userId) {

            "0" -> {
                ResponseState.Error(Exception("Error State Testing"))
            }

            "1" -> {
                ResponseState.NoDataFound
            }

            "2" -> {
                ResponseState.ServerError
            }

            "3.1" -> {
                ResponseState.Success(
                    provideRemoteUser()
                )
            }

            "3.2" -> {
                ResponseState.Success(
                    provideRemoteUser().copy(
                        team = RemoteTeam(
                            isRegistered = false
                        )
                    )
                )
            }

            "3.3" -> {
                ResponseState.Success(
                    provideRemoteUser().copy(
                        team = RemoteTeam(isRegistered = true)
                    )
                )
            }

            else -> {
                ResponseState.NoInternet
            }
        }
    }

    override suspend fun createTeam(): ResponseState<RemoteTeam> {
        return ResponseState.Success(provideRemoteTeam())
    }

    override suspend fun joinTeam(): ResponseState<RemoteTeam> {
        return ResponseState.Success(provideRemoteTeam())
    }

    override suspend fun registerTeam(): ResponseState<RemoteTeam> {
        return ResponseState.Success(provideRemoteTeam())
    }

    override suspend fun getTeamById(): ResponseState<RemoteTeam> {
        return ResponseState.Success(provideRemoteTeam())
    }

    private fun provideRemoteUser(): RemoteUser {
        return RemoteUser(
            id = "Test User Id 01",
            uid = null,
            name = "Anirban Basak",
            email = "email id",
            token = null,
            team = null,
            isLead = null,
            v = null
        )
    }

    private fun provideRemoteTeam(): RemoteTeam {
        return RemoteTeam(
            id = "Test Team Id 01",
            teamName = "Test Team 01",
            teamLead = provideRemoteUser()
        )
    }
}
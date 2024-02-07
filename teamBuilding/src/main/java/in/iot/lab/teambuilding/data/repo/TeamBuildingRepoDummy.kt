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
                    getUser(1)
                )
            }

            "3.2" -> {
                ResponseState.Success(
                    getUser(1)
                )
            }

            "3.3" -> {
                ResponseState.Success(
                    getUser(1)
                )
            }

            else -> {
                ResponseState.NoInternet
            }
        }
    }

    override suspend fun createTeam(): ResponseState<RemoteTeam> {
        delay(4000)
        return ResponseState.Success(createNewTeam(1))
    }

    override suspend fun joinTeam(): ResponseState<RemoteTeam> {
        delay(4000)
        return ResponseState.Success(getUnRegisteredTeam(2))
    }

    override suspend fun registerTeam(): ResponseState<RemoteTeam> {
        delay(4000)
        return ResponseState.Success(getUnRegisteredTeam(1).copy(isRegistered = true))
    }

    override suspend fun getTeamById(teamId: String): ResponseState<RemoteTeam> {
        delay(4000)
        return ResponseState.Success(getUnRegisteredTeam(3))
    }

    private fun getUser(number: Int): RemoteUser {
        return RemoteUser(
            id = "Test User Id $number",
            uid = "Test User Uid $number",
            name = "Test User Name $number",
            email = "Test user email $number",
            token = null,
            team = null,
            isLead = false,
            v = null
        )
    }

    private fun createNewTeam(number: Int): RemoteTeam {
        return RemoteTeam(
            id = "Test Team Id $number",
            teamName = "Test Team Name $number",
            teamLead = getUser(1),
            teamMembers = listOf(getUser(1)),
            score = 0,
            numMain = 0,
            numSide = 0,
            isRegistered = false
        )
    }

    private fun getUnRegisteredTeam(number: Int): RemoteTeam {

        val userList: MutableList<RemoteUser> = mutableListOf()
        for (i in 1..number)
            userList.add(getUser(i))

        return RemoteTeam(
            id = "Test Team Id $number",
            teamName = "Test Team Name $number",
            teamLead = userList[0],
            teamMembers = userList,
            score = 0,
            numMain = 0,
            numSide = 0,
            isRegistered = false
        )
    }
}
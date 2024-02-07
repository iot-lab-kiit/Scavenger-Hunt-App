package `in`.iot.lab.teambuilding.view.state

import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.state.UiState


/**
 * These are the state which define the registration state of the User.
 *
 * @property Idle means that the registration flow isn't started yet.
 * @property Loading means that the details are getting fetched from the Server
 * @property Registered This means that the user has completed registration and will be directed to
 * the Team Dashboard Screen.
 * @property InTeam This means that the User is currently in a Team but the team is not registered.
 * @property NotRegistered This means that the user is not registered yet and haven't joined any
 * team too.
 */
sealed interface UserRegistrationState<out T> {
    data object Idle : UserRegistrationState<Nothing>
    data object Loading : UserRegistrationState<Nothing>
    data class Registered<T>(val userData: T) : UserRegistrationState<T>
    data class InTeam<T>(val userData: T) : UserRegistrationState<T>
    data class NotRegistered<T>(val userData: T?) : UserRegistrationState<T>

    data class Error(val message: String) : UserRegistrationState<Nothing>
}


/**
 * This Extension function of [UiState] is made to convert the [UiState] object to a
 * [UserRegistrationState] object and use that to define the User's registration and team status.
 */
fun UiState<RemoteTeam>.toUserRegistrationState(): UserRegistrationState<RemoteTeam> {
    return when (this) {
        is UiState.Idle -> {
            UserRegistrationState.Idle
        }

        is UiState.Loading -> {
            UserRegistrationState.Loading
        }

        is UiState.Success -> {

            val teamData = this.data

            if (teamData.isRegistered == true)
                UserRegistrationState.Registered(teamData)
            else
                UserRegistrationState.InTeam(teamData)
        }

        is UiState.Failed -> {
            UserRegistrationState.Error(this.message)
        }
    }
}
package `in`.iot.lab.network.state


/**
 * This variable is used to defines the various types of states in an API call and each state is
 * defines by their respective data object classes
 *
 * @property Idle This means that the api is currently not being used
 * @property Loading This means that an api call is already being sent
 * @property Success This means that an api call is a success and we have received the required data
 * @property Failed This means that the api call is a failure and we didn't received data
 */
sealed interface UiState {
    data object Loading : UiState
    data object Idle : UiState
    data class Success<T>(val data: T) : UiState
    data class Failed(val message: String) : UiState
}
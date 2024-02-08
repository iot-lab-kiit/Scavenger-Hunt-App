package `in`.iot.lab.network.state

import java.lang.Exception


/**
 * This class is used to defines the various types of states in an API call and each state is
 * defines by their respective data object classes
 *
 * @property NoInternet This means that the network is not working properly
 * @property NoDataFound This means that the database is empty and no such data found
 * @property ServerError This means that there is a problem in the server
 * @property Success This means that an api call is a success and we have received the required data
 * @property Error This is used to indicate other random Errors
 */
sealed interface ResponseState<out T> {
    data object NoInternet : ResponseState<Nothing>
    data object NoDataFound : ResponseState<Nothing>
    data object ServerError : ResponseState<Nothing>
    data object UserNotAuthorized : ResponseState<Nothing>
    data object TeamAlreadyExists : ResponseState<Nothing>
    data object TeamInvalidSize : ResponseState<Nothing>
    data object TeamInvalidMainQuest : ResponseState<Nothing>
    data object TeamInvalidSideQuest : ResponseState<Nothing>
    data class Success<T>(val data: T) : ResponseState<T>
    data class Error(val exception: Exception) : ResponseState<Nothing>
}
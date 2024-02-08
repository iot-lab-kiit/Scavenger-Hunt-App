package `in`.iot.lab.network.utils

import `in`.iot.lab.network.data.models.Response
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkStatusCodes.DATA_DELETED
import `in`.iot.lab.network.utils.NetworkStatusCodes.DATA_NOT_FOUND
import `in`.iot.lab.network.utils.NetworkStatusCodes.DEFAULT_URL_ENTERED
import `in`.iot.lab.network.utils.NetworkStatusCodes.INTERNAL_SERVER_ERROR
import `in`.iot.lab.network.utils.NetworkStatusCodes.STATUS_OK
import `in`.iot.lab.network.utils.NetworkStatusCodes.TEAM_ALREADY_EXISTS
import `in`.iot.lab.network.utils.NetworkStatusCodes.TEAM_CREATED
import `in`.iot.lab.network.utils.NetworkStatusCodes.TEAM_INVALID_MAIN_QUEST
import `in`.iot.lab.network.utils.NetworkStatusCodes.TEAM_INVALID_SIDE_QUEST
import `in`.iot.lab.network.utils.NetworkStatusCodes.TEAM_INVALID_SIZE
import `in`.iot.lab.network.utils.NetworkStatusCodes.TEAM_REGISTERED
import `in`.iot.lab.network.utils.NetworkStatusCodes.TEAM_UPDATED
import `in`.iot.lab.network.utils.NetworkStatusCodes.USER_AUTHORIZED
import `in`.iot.lab.network.utils.NetworkStatusCodes.USER_CREATED
import `in`.iot.lab.network.utils.NetworkStatusCodes.USER_NOT_AUTHORIZED
import `in`.iot.lab.network.utils.NetworkStatusCodes.USER_UPDATED
import java.io.IOException

object NetworkUtil {


    /**
     * This function is a wrapper function over the Retrofit Api calls to make the exception
     * handling easier and less boilerplate code needs to be generated
     */
    suspend fun <T> getResponseState(
        onSuccess: suspend () -> Unit = {},
        onFailure: suspend (Exception) -> Unit = {},
        request: suspend () -> Response<T>
    ): ResponseState<T> {

        return try {

            // Response from the Retrofit Api call
            val response = request()
            onSuccess()

            if (response.data == null)
                ResponseState.NoDataFound
            else
                checkApiResponseStatusCode(response)
        } catch (exception: IOException) {
            ResponseState.NoInternet
        } catch (e: Exception) {

            // Calling the Custom Failure Function
            onFailure(e)
            ResponseState.Error(e)
        }
    }

    private fun <T> checkApiResponseStatusCode(response: Response<T>): ResponseState<T> {
        return when (response.status) {

            STATUS_OK, USER_CREATED, USER_AUTHORIZED, USER_UPDATED, DEFAULT_URL_ENTERED,
            TEAM_CREATED, TEAM_UPDATED, TEAM_REGISTERED, DATA_DELETED -> ResponseState.Success(data = response.data)

            USER_NOT_AUTHORIZED -> ResponseState.UserNotAuthorized
            TEAM_ALREADY_EXISTS -> ResponseState.TeamAlreadyExists
            TEAM_INVALID_SIZE -> ResponseState.TeamInvalidSize
            TEAM_INVALID_MAIN_QUEST -> ResponseState.TeamInvalidMainQuest
            TEAM_INVALID_SIDE_QUEST -> ResponseState.TeamInvalidSideQuest
            DATA_NOT_FOUND -> ResponseState.NoDataFound
            INTERNAL_SERVER_ERROR -> ResponseState.ServerError
            else -> ResponseState.Error(Exception("Unknown Error Occurred !!"))
        }
    }


    /**
     * This function is converting the [ResponseState] objects into [UiState] objects for later used
     * and passed down to the View Model Layer
     */
    fun <T> ResponseState<T>.toUiState(): UiState<T> {
        return when (this) {
            is ResponseState.NoInternet -> {
                UiState.Failed("Your Internet is not working please check and try again Later !!")
            }

            is ResponseState.NoDataFound -> {
                UiState.Failed("There are No Records in the Database! Try again later")
            }

            is ResponseState.ServerError -> {
                UiState.Failed("There are some issues with the server! Please Try again after sometime")
            }

            is ResponseState.UserNotAuthorized -> {
                UiState.Failed("User Not Authorized")
            }

            is ResponseState.TeamAlreadyExists -> {
                UiState.Failed("Team Already Exists")
            }

            is ResponseState.TeamInvalidSize -> {
                UiState.Failed("Invalid Team Size. Team should have 3 to 5 members")
            }

            is ResponseState.TeamInvalidMainQuest -> {
                UiState.Failed("Invalid Main Quest Scanned")
            }

            is ResponseState.TeamInvalidSideQuest -> {
                UiState.Failed("Invalid Side Quest Scanned")
            }

            is ResponseState.Success -> {
                UiState.Success(this.data)
            }

            is ResponseState.Error -> {
                UiState.Failed(this.exception.message.toString())
            }
        }
    }
}
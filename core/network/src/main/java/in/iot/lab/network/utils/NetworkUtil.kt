package `in`.iot.lab.network.utils

import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.state.UiState
import retrofit2.Response
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

            // Checking if the Api call is a success or not
            if (response.isSuccessful) {

                // Calling the Custom success function
                onSuccess()

                // Retrofit Response body containing the actual data
                val data = response.body()

                // Checking if the data is empty or not
                if (data != null)
                    ResponseState.Success(data = data)
                else
                    ResponseState.NoDataFound

            } else
                return ResponseState.ServerError
        } catch (exception: IOException) {
            ResponseState.NoInternet
        } catch (e: Exception) {

            // Calling the Custom Failure Function
            onFailure(e)
            ResponseState.Error(e)
        }
    }


    /**
     * This function is a wrapper function over the Retrofit Api calls to make the exception
     * handling easier and less boilerplate code needs to be generated
     */
    suspend fun <T> getResponseState(
        onSuccess: suspend () -> Unit = {},
        onFailure: suspend (Exception) -> Unit = {},
        request: suspend () -> T
    ): ResponseState<T> {

        return try {

            // Response from the Api call
            val res = request()

            // Calling the Custom success function
            onSuccess()
            ResponseState.Success(res)

        } catch (e: IOException) {
            ResponseState.NoInternet
        } catch (e: Exception) {

            // Calling the Custom Failure Function
            onFailure(e)
            ResponseState.Error(e)
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

            is ResponseState.Success -> {
                UiState.Success(this.data)
            }

            is ResponseState.Error -> {
                UiState.Failed(this.exception.message.toString())
            }
        }
    }
}
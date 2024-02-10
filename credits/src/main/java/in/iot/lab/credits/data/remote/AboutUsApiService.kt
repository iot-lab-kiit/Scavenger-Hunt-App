package `in`.iot.lab.credits.data.remote

import `in`.iot.lab.credits.data.models.RemoteAboutUs
import `in`.iot.lab.network.data.models.Response
import `in`.iot.lab.network.utils.NetworkConstants
import retrofit2.http.GET

interface AboutUsApiService {

    @GET(NetworkConstants.GET_CREDITS_ENDPOINT)
    suspend fun getCredits(): Response<RemoteAboutUs>

}
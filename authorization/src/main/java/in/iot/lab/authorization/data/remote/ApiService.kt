package `in`.iot.lab.authorization.data.remote

import `in`.iot.lab.authorization.domain.model.AuthRequest
import `in`.iot.lab.authorization.domain.model.AuthResponse
import `in`.iot.lab.network.data.models.Response
import `in`.iot.lab.network.utils.NetworkConstants
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST(NetworkConstants.AUTH_ENDPOINT)
    suspend fun postAuthentication(@Body request: AuthRequest): Response<AuthResponse>
}

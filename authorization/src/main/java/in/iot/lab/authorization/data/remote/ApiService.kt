package `in`.iot.lab.authorization.data.remote

import `in`.iot.lab.authorization.Constants
import `in`.iot.lab.authorization.domain.model.AuthRequest
import `in`.iot.lab.authorization.domain.model.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST(Constants.AUTH_ENDPOINT)
    suspend fun postAuthentication(@Body request: AuthRequest): AuthResponse
}

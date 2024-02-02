package `in`.iot.lab.authorization.data.remote

import `in`.iot.lab.authorization.Constants
import `in`.iot.lab.authorization.domain.model.AuthenticationResponse
import `in`.iot.lab.authorization.domain.model.PostAuthenticationRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST(Constants.AUTH_ENDPOINT)
    suspend fun postAuthentication(@Body request: PostAuthenticationRequest): AuthenticationResponse
}

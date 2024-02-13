package `in`.iot.lab.authorization.domain.repository

import android.content.Context
import `in`.iot.lab.authorization.domain.model.AuthResponse
import `in`.iot.lab.authorization.domain.model.AuthResult
import `in`.iot.lab.authorization.domain.model.User
import `in`.iot.lab.network.state.ResponseState

interface AuthRepository {
    val currentUser: User?
    suspend fun signIn(context: Context): AuthResult
    suspend fun logout()
    suspend fun authenticateUserOnServer(token: String): ResponseState<AuthResponse>
    suspend fun loginWithFirebase(idToken: String): AuthResult
}
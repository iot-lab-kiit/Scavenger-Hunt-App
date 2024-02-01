package `in`.iot.lab.authorization.domain.repository

import android.content.Context
import `in`.iot.lab.authorization.domain.model.AuthResult
import `in`.iot.lab.authorization.domain.model.AuthenticationResponse
import `in`.iot.lab.authorization.domain.model.User

interface AuthRepository {
    val currentUser: User?
    suspend fun signIn(context: Context): AuthResult
    suspend fun logout()
    suspend fun authenticateUserOnServer(token: String): AuthenticationResponse
}
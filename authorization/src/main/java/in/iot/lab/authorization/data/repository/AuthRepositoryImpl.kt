package `in`.iot.lab.authorization.data.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import `in`.iot.lab.authorization.data.remote.ApiService
import `in`.iot.lab.authorization.domain.model.AuthResult
import `in`.iot.lab.authorization.domain.model.AuthenticationResponse
import `in`.iot.lab.authorization.domain.model.User
import `in`.iot.lab.authorization.domain.model.toUser
import `in`.iot.lab.authorization.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val request: GetCredentialRequest,
    private val credentialManager: CredentialManager,
    private val apiService: ApiService,
): AuthRepository {
    override val currentUser: User?
        get() = auth.currentUser.toUser()

    override suspend fun signIn(context: Context): AuthResult {
        val result = credentialManager.getCredential(
            request = request,
            context = context,
        )
        return handleSignIn(result)
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun authenticateUserOnServer(token: String): AuthenticationResponse {
//        return apiService.postAuthentication(
//            PostAuthenticationRequest(
//                token = token
//            )
//        )
        // TODO: Remove this once the server is ready
        return AuthenticationResponse(
            success = true
        )
    }

    private suspend fun handleSignIn(result: GetCredentialResponse): AuthResult {
        return when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val email = googleIdTokenCredential.id
                        if (!email.isKiitEmail()) {
                            throw Exception("Please use your KIIT email to login")
                        }
                        val idToken = googleIdTokenCredential.idToken
                        loginWithFirebase(idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        throw Exception("Received an invalid google id token response")
                    }
                } else {
                    throw Exception("Unexpected type of credential")
                }
            }

            else -> {
                throw Exception("Unexpected type of credential")
            }
        }
    }
    private suspend fun loginWithFirebase(idToken: String): AuthResult {
        val googleCredentials = GoogleAuthProvider.getCredential(idToken, null)
        val firebaseUser = auth.signInWithCredential(googleCredentials).await().user
        return AuthResult(
            data = firebaseUser.toUser()
        )
    }

    private fun String.isKiitEmail(): Boolean {
        return this.endsWith("@kiit.ac.in")
    }
}
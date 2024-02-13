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
import `in`.iot.lab.authorization.domain.model.AuthRequest
import `in`.iot.lab.authorization.domain.model.AuthResponse
import `in`.iot.lab.authorization.domain.model.AuthResult
import `in`.iot.lab.authorization.domain.model.User
import `in`.iot.lab.authorization.domain.model.toUser
import `in`.iot.lab.authorization.domain.repository.AuthRepository
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
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

    override suspend fun authenticateUserOnServer(token: String): ResponseState<AuthResponse> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.postAuthentication(
                    AuthRequest(token = token)
                )
            }
        }
    }

    private suspend fun handleSignIn(result: GetCredentialResponse): AuthResult {
        return when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
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
    override suspend fun loginWithFirebase(idToken: String): AuthResult {
        val googleCredentials = GoogleAuthProvider.getCredential(idToken, null)
        val firebaseUser = auth.signInWithCredential(googleCredentials).await().user
        return AuthResult(
            data = firebaseUser.toUser()
        )
    }
}
package `in`.iot.lab.authorization.domain.usecase

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.credentials.exceptions.GetCredentialUnsupportedException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import `in`.iot.lab.authorization.domain.model.AuthResult
import `in`.iot.lab.authorization.domain.repository.AuthRepository
import `in`.iot.lab.authorization.ui.screen.SignInLauncher
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.await
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
) {
    suspend operator fun invoke(
        context: Context,
        signInLauncher: SignInLauncher
    ) = flow {
        try {
            emit(UiState.Loading)
            val result = repository.signIn(context)
            if (result.data != null) {
                authenticateOnServer(result)
            } else {
                emit(UiState.Failed("Something went wrong"))
            }
        } catch (e: Exception) {
            if (e is GetCredentialUnsupportedException) {
                Log.e(
                    "SignInUseCase",
                    "Credential Manager not supported on this device, so we are switching to legacy login flow"
                )
                // Launch the legacy Google SignIn flow
                signInLauncher.launch(googleSignInClient.signInIntent)
            } else {
                emit(UiState.Failed(e.message ?: "Something went wrong"))
            }
        }
    }

    suspend fun handleSignInResult(result: ActivityResult) = flow {
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val idToken = account.idToken!!
                val authResult = repository.loginWithFirebase(idToken)
                authenticateOnServer(authResult)
            } catch (e: ApiException) {
                emit(UiState.Failed("Google sign in failed"))
            } catch (e: Exception) {
                Log.e("SignInUseCase", "Error: ${e.message}")
                emit(UiState.Failed(e.message ?: "Something went wrong"))
            }
        }
    }

    private suspend fun FlowCollector<UiState<AuthResult>>.authenticateOnServer(
        result: AuthResult
    ) {
        val userIdToken = auth.currentUser!!.getIdToken(false).await().token!!
        when (val postAuthApiResult = repository.authenticateUserOnServer(userIdToken)) {
            is ResponseState.Success -> {
                emit(UiState.Success(result))
            }

            is ResponseState.Error -> {
                googleSignInClient.signOut().await()
                repository.logout()
                emit(UiState.Failed(postAuthApiResult.exception.message.toString()))
            }

            else -> {
                repository.logout()
                emit(UiState.Failed("Something Went Wrong with the server, Please try again later."))
            }
        }
    }
}
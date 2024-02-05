package `in`.iot.lab.authorization.domain.usecase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import `in`.iot.lab.authorization.data.utils.await
import `in`.iot.lab.authorization.domain.repository.AuthRepository
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.state.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val auth: FirebaseAuth,
) {
    suspend operator fun invoke(context: Context) = flow {
        try {
            emit(UiState.Loading)
            val result = repository.signIn(context)
            if (result.data != null) {
                val userIdToken = auth.currentUser!!.getIdToken(false).await().token!!
                val postAuthApiResult = repository.authenticateUserOnServer(userIdToken)
                // TODO: Remove this delay once the server is ready
                delay(2000)
                if (postAuthApiResult is ResponseState.Success && postAuthApiResult.data.user != null) {
                    emit(UiState.Success(result))
                } else {
                    // Logout the user if the server authentication fails as we are navigating
                    // to teamBuilding screen based on if the current user is not or not
                    repository.logout()
                    emit(UiState.Failed("Something went wrong"))
                }
            } else {
                emit(UiState.Failed("Something went wrong"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "Something went wrong"))
        }
    }
}
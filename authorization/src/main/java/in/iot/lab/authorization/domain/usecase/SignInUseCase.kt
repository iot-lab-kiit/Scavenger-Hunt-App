package `in`.iot.lab.authorization.domain.usecase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import `in`.iot.lab.authorization.domain.repository.AuthRepository
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.await
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
                when (val postAuthApiResult = repository.authenticateUserOnServer(userIdToken)) {
                    is ResponseState.Success -> {
                        emit(UiState.Success(result))
                    }

                    is ResponseState.Error -> {
                        repository.logout()
                        emit(UiState.Failed(postAuthApiResult.exception.message.toString()))
                    }

                    else -> {
                        repository.logout()
                        emit(UiState.Failed("Something Went Wrong"))
                    }
                }
            } else {
                emit(UiState.Failed("Something went wrong"))
            }
        } catch (e: Exception) {
            emit(UiState.Failed(e.message ?: "Something went wrong"))
        }
    }
}
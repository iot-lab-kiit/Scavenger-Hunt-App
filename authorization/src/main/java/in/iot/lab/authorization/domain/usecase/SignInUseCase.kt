package `in`.iot.lab.authorization.domain.usecase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import `in`.iot.lab.authorization.data.utils.await
import `in`.iot.lab.authorization.domain.model.Result
import `in`.iot.lab.authorization.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val auth: FirebaseAuth,
) {
    suspend operator fun invoke(context: Context) = flow {
        try {
            emit(Result.Loading)
            val result = repository.signIn(context)
            if (result.data != null) {
                val userIdToken = auth.currentUser!!.getIdToken(false).await().token!!
                val postAuthApiResult = repository.authenticateUserOnServer(userIdToken)
                // TODO: Remove this delay once the server is ready
                delay(2000)
                if (postAuthApiResult.success) {
                    emit(Result.Success(result))
                }
            } else {
                emit(Result.Error(Exception("Something went wrong")))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}
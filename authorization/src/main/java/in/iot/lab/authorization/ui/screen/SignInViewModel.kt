package `in`.iot.lab.authorization.ui.screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.authorization.domain.model.Result
import `in`.iot.lab.authorization.domain.model.User
import `in`.iot.lab.authorization.domain.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state

    fun signIn(context: Context) {
        viewModelScope.launch {
            signInUseCase.invoke(context).collect {
                when (it) {
                    is Result.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = it.exception.message
                        )
                        Log.e("SignInViewModel", "signIn: ", it.exception)
                    }

                    Result.Loading -> {
                        Log.d("SignInViewModel", "signIn: Loading")
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Result.Success -> {
                        Log.d("SignInViewModel", "signIn: Success")
                        _state.value = _state.value.copy(
                            isLoading = false,
                            user = it.data.data,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }
}

data class SignInState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val user: User? = null
)

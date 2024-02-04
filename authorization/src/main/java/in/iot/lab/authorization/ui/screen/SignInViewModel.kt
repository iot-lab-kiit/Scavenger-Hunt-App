package `in`.iot.lab.authorization.ui.screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.authorization.domain.model.User
import `in`.iot.lab.authorization.domain.usecase.SignInUseCase
import `in`.iot.lab.network.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: Maybe add init block to check if user is already signed in and navigate to Home Screen
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
                    is UiState.Failed -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                        Log.e("SignInViewModel", "signIn: ${it.message}")
                    }

                    UiState.Loading -> {
                        Log.d("SignInViewModel", "signIn: Loading")
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is UiState.Success -> {
                        Log.d("SignInViewModel", "signIn: Success")
                        _state.value = _state.value.copy(
                            isLoading = false,
                            user = it.data.data,
                            errorMessage = null
                        )
                    }
                    UiState.Idle -> {}
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

package `in`.iot.lab.authorization.ui.screen

import android.content.Context
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.authorization.domain.model.AuthResult
import `in`.iot.lab.authorization.domain.usecase.SignInUseCase
import `in`.iot.lab.network.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<AuthResult>>(UiState.Idle)
    val state = _state.asStateFlow()


    fun signIn(context: Context, signInLauncher: SignInLauncher) {
        viewModelScope.launch {
            signInUseCase.invoke(
                context = context,
                signInLauncher = signInLauncher
            ).collect {
                _state.value = it
            }
        }
    }

    // Result From Legacy SignIn Flow
    fun onSignInResult(result: ActivityResult) {
        viewModelScope.launch {
            signInUseCase.handleSignInResult(
                result = result
            ).collect {
                _state.value = it
            }
        }
    }
}
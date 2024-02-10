package `in`.iot.lab.credits.view.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.credits.data.models.RemoteAboutUs
import `in`.iot.lab.credits.data.repo.AboutUsRepo
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkUtil.toUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AboutUsViewModel @Inject constructor(
    private val repository: AboutUsRepo
) : ViewModel() {

    private val _creditsData = MutableStateFlow<UiState<RemoteAboutUs>>(UiState.Idle)
    val creditsData = _creditsData.asStateFlow()


    init {
        getCreditsData()
    }


    fun getCreditsData() {

        if (_creditsData.value is UiState.Idle)
            return

        _creditsData.value = UiState.Idle

        viewModelScope.launch {

            _creditsData.value = repository
                .getCredits()
                .toUiState()
        }
    }
}
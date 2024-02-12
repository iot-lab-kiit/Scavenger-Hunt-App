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

    private val _creditsData = MutableStateFlow<UiState<List<RemoteAboutUs>>>(UiState.Idle)
    val creditsData = _creditsData.asStateFlow()

    fun getCreditsData() {

        if (_creditsData.value is UiState.Loading)
            return

        _creditsData.value = UiState.Loading

        viewModelScope.launch {

            val response = repository
                .getCredits()
                .toUiState()

            val filteredList: MutableList<RemoteAboutUs> = mutableListOf()
            filteredList.add(hardCodedData())
            var indexPriyanshu = 0

            if (response is UiState.Success) {
                response.data.forEachIndexed { index, remoteAboutUs ->
                    remoteAboutUs.name?.let {
                        if (it.contains("Priyanshu"))
                            indexPriyanshu = index
                        else if (it.contains("Anirban")) {
                            // Do Nothing
                        } else
                            filteredList.add(remoteAboutUs)
                    }
                }
                filteredList.add(response.data[indexPriyanshu])

                _creditsData.value = UiState.Success(filteredList)
            } else
                _creditsData.value = response
        }
    }

    private fun hardCodedData(): RemoteAboutUs {
        return RemoteAboutUs(
            name = "Anirban Basak",
            designation = "Organiser , App Developer , Smartest Person Alive",
            instagramLink = null,
            githubLink = "https://github.com/basakjeet08",
            linkedIn = "https://www.linkedin.com/in/anirban-basak-b96055249/"
        )
    }
}
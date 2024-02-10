package `in`.iot.lab.credits.data.repo

import `in`.iot.lab.credits.data.models.RemoteAboutUs
import `in`.iot.lab.credits.data.remote.AboutUsApiService
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AboutUsRepoImpl @Inject constructor(
    private val apiService: AboutUsApiService
) : AboutUsRepo {

    override suspend fun getCredits(): ResponseState<RemoteAboutUs> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.getCredits()
            }
        }
    }
}
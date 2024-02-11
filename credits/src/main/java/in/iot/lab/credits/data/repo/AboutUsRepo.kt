package `in`.iot.lab.credits.data.repo

import `in`.iot.lab.credits.data.models.RemoteAboutUs
import `in`.iot.lab.network.state.ResponseState

interface AboutUsRepo {

    suspend fun getCredits(): ResponseState<List<RemoteAboutUs>>
}
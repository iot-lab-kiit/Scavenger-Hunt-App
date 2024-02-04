package `in`.iot.lab.network.data.models.route

import com.google.gson.annotations.SerializedName
import `in`.iot.lab.network.data.models.hint.RemoteHint

data class RemoteRoute(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("theme")
    val theme: String? = null,
    @SerializedName("hints")
    val hints: List<RemoteHint>? = null
)
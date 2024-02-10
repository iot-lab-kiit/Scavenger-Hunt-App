package `in`.iot.lab.network.data.models.user

import com.google.gson.annotations.SerializedName


data class RemoteUser(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("team")
    val team: String? = null,
    @SerializedName("isLead")
    val isLead: Boolean? = null
)
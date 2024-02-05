package `in`.iot.lab.network.data.models.user

import com.google.gson.annotations.SerializedName
import `in`.iot.lab.network.data.models.team.RemoteTeam

data class RemoteUser(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("uid")
    val uid: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("team")
    val team: RemoteTeam? = null,
    @SerializedName("isLead")
    val isLead: String? = null,
    @SerializedName("__v")
    val v: Int? = null
)
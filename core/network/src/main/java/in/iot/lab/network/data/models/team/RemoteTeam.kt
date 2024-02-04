package `in`.iot.lab.network.data.models.team

import com.google.gson.annotations.SerializedName
import `in`.iot.lab.network.data.models.hint.RemoteHint
import `in`.iot.lab.network.data.models.route.RemoteRoute
import `in`.iot.lab.network.data.models.user.RemoteUser

data class RemoteTeam(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("teamName")
    val teamName: String? = null,
    @SerializedName("teamLead")
    val teamLead: RemoteUser? = null,
    @SerializedName("teamMembers")
    val teamMembers: List<RemoteUser>? = null,
    @SerializedName("score")
    val score: Int? = null,
    @SerializedName("numMain")
    val numMain: Int? = null,
    @SerializedName("numSide")
    val numSide: Int? = null,
    @SerializedName("mainQuest")
    val mainQuest: List<RemoteHint>? = null,
    @SerializedName("sideQuest")
    val sideQuest: List<RemoteHint>? = null,
    @SerializedName("route")
    val route: RemoteRoute? = null,
    @SerializedName("isRegistered")
    val isRegistered: Boolean? = null
)

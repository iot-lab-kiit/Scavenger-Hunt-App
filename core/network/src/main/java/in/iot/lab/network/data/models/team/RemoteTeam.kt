package `in`.iot.lab.network.data.models.team

import com.google.gson.annotations.SerializedName
import `in`.iot.lab.network.data.models.hint.RemoteHint
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
    @SerializedName("isRegistered")
    val isRegistered: Boolean? = null,
    @SerializedName("totalMain")
    val totalMain: Int = 1,
    @SerializedName("totalSide")
    val totalSide: Int = 1,
    @SerializedName("theme")
    val theme: String? = null,
    @SerializedName("doc")
    val themeDoc: String? = null
)

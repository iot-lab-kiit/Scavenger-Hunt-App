package `in`.iot.lab.teambuilding.data.model

import com.google.gson.annotations.SerializedName

data class UpdateTeamBody(
    @SerializedName("userid")
    val userId: String,
    @SerializedName("isRegistered")
    val isRegistered: Boolean = false
)
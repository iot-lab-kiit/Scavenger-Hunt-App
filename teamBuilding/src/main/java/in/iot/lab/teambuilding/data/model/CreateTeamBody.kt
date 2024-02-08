package `in`.iot.lab.teambuilding.data.model

import com.google.gson.annotations.SerializedName

data class CreateTeamBody(
    @SerializedName("teamName")
    val teamName: String,
    @SerializedName("teamLead")
    val teamLead: String,
    @SerializedName("teamMembers")
    val teamMembers: List<String>
)
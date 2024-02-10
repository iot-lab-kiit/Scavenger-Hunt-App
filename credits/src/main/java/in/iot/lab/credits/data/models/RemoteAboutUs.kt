package `in`.iot.lab.credits.data.models

import com.google.gson.annotations.SerializedName


data class RemoteAboutUs(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("designation")
    val designation: String? = null,
    @SerializedName("instagram")
    val instagramLink: String? = null,
    @SerializedName("github")
    val githubLink: String? = null,
    @SerializedName("linkedIn")
    val linkedIn: String? = null
)
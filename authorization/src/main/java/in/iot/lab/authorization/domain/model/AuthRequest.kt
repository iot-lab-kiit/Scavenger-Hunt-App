package `in`.iot.lab.authorization.domain.model

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("token")
    val token: String
)

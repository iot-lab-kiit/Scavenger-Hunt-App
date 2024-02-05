package `in`.iot.lab.authorization.domain.model


import com.google.gson.annotations.SerializedName
import `in`.iot.lab.network.data.models.user.RemoteUser

data class AuthResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("user")
    val user: RemoteUser? = null
)
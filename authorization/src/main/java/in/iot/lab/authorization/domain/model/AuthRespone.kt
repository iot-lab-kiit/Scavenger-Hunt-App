package `in`.iot.lab.authorization.domain.model


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("user")
    val user: RemoteUser? = null
)

data class RemoteUser(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("uid")
    val uid: String? = null,
    @SerializedName("isLead")
    val isLead: Boolean? = null,
    @SerializedName("__v")
    val v: Int? = null
)
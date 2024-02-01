package `in`.iot.lab.authorization.domain.model

data class AuthenticationResponse(
    val success: Boolean,
)

data class PostAuthenticationRequest(
    val token: String,
)

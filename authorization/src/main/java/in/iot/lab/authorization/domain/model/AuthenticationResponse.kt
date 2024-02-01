package `in`.iot.lab.authorization.domain.model

// TODO: Change this once the server is ready
data class AuthenticationResponse(
    val success: Boolean,
)

data class PostAuthenticationRequest(
    val token: String,
)

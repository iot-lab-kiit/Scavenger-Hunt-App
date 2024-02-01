package `in`.iot.lab.authorization.domain.model

import com.google.firebase.auth.FirebaseUser

data class AuthResult(
    val data: User?,
)

data class User(
    val username: String?,
    val uid: String,
    val email: String,
    val photoUrl: String?,
)

fun FirebaseUser?.toUser(): User? {
    return this?.run {
        User(
            uid = uid,
            email = email ?: "",
            photoUrl = photoUrl?.toString(),
            username = displayName
        )
    }
}
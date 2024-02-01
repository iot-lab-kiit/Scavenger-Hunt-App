package `in`.iot.lab.authorization.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.iot.lab.authorization.domain.model.User
import `in`.iot.lab.design.theme.ScavengerHuntTheme

@Composable
internal fun SignInRoute(
    viewModel: SignInViewModel = hiltViewModel(),
    onUserSignedIn: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(state.user) {
        if (state.user != null) {
            onUserSignedIn()
        }
    }
    SignInScreen(
        state = state,
        onLoginClicked = { viewModel.signIn(context) }
    )
}

@Composable
internal fun SignInScreen(
    state: SignInState = SignInState(),
    onLoginClicked: () -> Unit = {}
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = onLoginClicked) {
                Text(text = "Sign In With Google")
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            if (state.user != null) {
                Text(
                    text = "Welcome ${state.user.username}",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = state.user.email,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
private fun SignInPreview() {
    ScavengerHuntTheme {
        SignInScreen(
            state = SignInState(
                isLoading = true,
            )
        )
    }
}

@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
private fun SignInPreviewError() {
    ScavengerHuntTheme {
        SignInScreen(
            state = SignInState(
                isLoading = false,
                errorMessage = "Please use your KIIT email to login"
            )
        )
    }
}

@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
private fun SignInPreviewSuccess() {
    ScavengerHuntTheme {
        SignInScreen(
            state = SignInState(
                isLoading = false,
                errorMessage = null,
                user = User(
                    uid = "id",
                    username = "John Doe",
                    email = "email@email.com",
                    photoUrl = "photoUrl"
                )
            )
        )
    }
}
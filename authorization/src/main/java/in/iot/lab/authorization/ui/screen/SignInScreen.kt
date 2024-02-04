package `in`.iot.lab.authorization.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.iot.lab.authorization.domain.model.User
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.TheMatrixHeaderUI
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

// TODO: Use better UI
@Composable
internal fun SignInScreen(
    state: SignInState = SignInState(),
    onLoginClicked: () -> Unit = {}
) {
    AppScreen {
        Image(
            painter = painterResource(id = R.drawable.matrix_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TheMatrixHeaderUI()

            Spacer(modifier = Modifier.height(150.dp))

            PrimaryButton(
                onClick = onLoginClicked,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth()
                    .height(height = 56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = `in`.iot.lab.authorization.R.drawable.ic_google),
                        contentDescription = "Google Sign In",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(32.dp)
                    )
                    Text(
                        text = "Sign In with Google",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
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
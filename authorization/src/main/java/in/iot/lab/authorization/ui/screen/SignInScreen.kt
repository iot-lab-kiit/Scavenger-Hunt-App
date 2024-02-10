package `in`.iot.lab.authorization.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.iot.lab.authorization.R
import `in`.iot.lab.authorization.domain.model.User
import `in`.iot.lab.design.components.AppBackgroundImage
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.SecondaryButton
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


@Composable
internal fun SignInScreen(
    state: SignInState = SignInState(),
    onLoginClicked: () -> Unit = {}
) {
    AppScreen {

        // Back Ground Image
        AppBackgroundImage()


        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Matrix Header
            TheMatrixHeaderUI()

            // Sign in with google button
            SecondaryButton(
                modifier = Modifier
                    .padding(horizontal = 56.dp)
                    .fillMaxWidth(),
                onClick = onLoginClicked,
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(12.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    // Sign in with text
                    Text(
                        text = "SIGN IN WITH",
                        fontStyle = FontStyle(`in`.iot.lab.design.R.font.montserratsemibold),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )

                    // Google Logo
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google Sign In",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Error Message
        if (state.errorMessage != null) {
            ErrorDialog(text = state.errorMessage)
        }


        // Loading State
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 56.dp)
            )
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
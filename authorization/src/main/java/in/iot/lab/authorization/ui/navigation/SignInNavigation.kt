package `in`.iot.lab.authorization.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import `in`.iot.lab.authorization.ui.screen.SignInRoute

const val SIGNIN_ROUTE = "signin_route"

// TODO: add navigateToSignIn in Main Navigation Graph
fun NavController.navigateToSignin(navOptions: NavOptions) = navigate(SIGNIN_ROUTE, navOptions)

fun NavGraphBuilder.signInScreen(
    onUserSignedIn: () -> Unit = {}
) {
    composable(route = SIGNIN_ROUTE) {
        SignInRoute(onUserSignedIn = onUserSignedIn)
    }
}
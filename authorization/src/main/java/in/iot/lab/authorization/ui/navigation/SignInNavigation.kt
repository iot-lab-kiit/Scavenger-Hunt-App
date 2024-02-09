package `in`.iot.lab.authorization.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import `in`.iot.lab.authorization.ui.screen.SignInRoute

const val SIGN_IN_ROUTE = "sign_in_route"

fun NavController.navigateToSignIn(navOptions: NavOptions) = navigate(SIGN_IN_ROUTE, navOptions)

fun NavGraphBuilder.signInScreen(
    onUserSignedIn: () -> Unit = {}
) {
    composable(
        route = SIGN_IN_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { fadeOut(animationSpec = tween(700)) }
    ) {
        SignInRoute(onUserSignedIn = onUserSignedIn)
    }
}
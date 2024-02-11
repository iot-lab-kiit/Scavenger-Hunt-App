package `in`.iot.lab.credits.view.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import `in`.iot.lab.credits.view.screens.AboutUsScreen
import `in`.iot.lab.credits.view.vm.AboutUsViewModel
import `in`.iot.lab.design.animation.navigation.entry.appFadeInTransition
import `in`.iot.lab.design.animation.navigation.exit.appFadeOutTransition

const val ABOUT_US_ROUTE = "about_us_route"

fun NavController.navigateToAboutUsScreen(navOptions: NavOptions? = null) {
    this.navigate(ABOUT_US_ROUTE, navOptions)
}


fun NavGraphBuilder.aboutUsNavGraph(
    navController: NavController,
    onBackPress: () -> Unit
) {

    composable(
        ABOUT_US_ROUTE,
        enterTransition = { appFadeInTransition() },
        exitTransition = { appFadeOutTransition() },
        popExitTransition = { appFadeOutTransition() },
        popEnterTransition = { appFadeInTransition() }
    ) {

        val viewModel = hiltViewModel<AboutUsViewModel>()

        val aboutUsData = viewModel.creditsData.collectAsState().value

        AboutUsScreen(
            aboutUsData = aboutUsData,
            onCancelClick = onBackPress,
            onRetryClick = viewModel::getCreditsData
        )
    }
}
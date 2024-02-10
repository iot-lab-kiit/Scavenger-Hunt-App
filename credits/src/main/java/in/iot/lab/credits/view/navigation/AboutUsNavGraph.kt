package `in`.iot.lab.credits.view.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import `in`.iot.lab.credits.view.screens.AboutUsScreen
import `in`.iot.lab.credits.view.vm.AboutUsViewModel
import `in`.iot.lab.network.state.UiState

const val ABOUT_US_ROUTE = "about_us_route"

fun NavController.navigateToAboutUsScreen(navOptions: NavOptions? = null) {
    this.navigate(ABOUT_US_ROUTE, navOptions)
}


fun NavGraphBuilder.aboutUsNavGraph(
    navController: NavController,
    onBackPress: () -> Unit
) {

    composable(ABOUT_US_ROUTE) {

        val viewModel = hiltViewModel<AboutUsViewModel>()

//        val aboutUsData = viewModel.creditsData.collectAsState().value

        AboutUsScreen(
            aboutUsData = UiState.Idle,
            onCancelClick = onBackPress,
            onRetryClick = viewModel::getCreditsData
        )
    }
}
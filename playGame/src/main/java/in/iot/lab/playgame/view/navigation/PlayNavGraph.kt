package `in`.iot.lab.playgame.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import `in`.iot.lab.playgame.view.screens.PlayHintScreenControl
import `in`.iot.lab.playgame.view.screens.PlayScannerScreenControl


// Play Game Routes
const val PLAY_GAME_ROOT_ROUTE = "play_game_root_route"
const val PLAY_GAME_SCANNER_ROUTE = "play_game_scanner_route"
const val PLAY_GAME_HINT_ROUTE = "play_game_hint_route"


/**
 * This function is used to navigate to the [PLAY_GAME_ROOT_ROUTE] nav graph
 *
 * @param navOptions This is the navigation option where we can define the navigation logic while
 * calling.
 */
fun NavController.navigateToPlay(navOptions: NavOptions) {
    this.navigate(PLAY_GAME_ROOT_ROUTE, navOptions)
}


/**
 * This function contains the Navigation graph for the Play Game Feature.
 *
 * @param onBackPress This function is invoked when the user hits back from the play game feature.
 */
fun NavGraphBuilder.playNavGraph(
    onBackPress: () -> Unit
) {

    navigation(
        route = PLAY_GAME_ROOT_ROUTE,
        startDestination = PLAY_GAME_SCANNER_ROUTE
    ) {

        // Scanner Screen
        composable(PLAY_GAME_SCANNER_ROUTE) {
            PlayScannerScreenControl()
        }

        // Hints Screen
        composable(PLAY_GAME_HINT_ROUTE) {
            PlayHintScreenControl()
        }
    }

}

/**
 * This function creates a [ViewModel] scoped to the Nav Graph so that the same view Model can be
 * used across the whole app.
 *
 * @param navController This is the nav controller which can be used to get the nav graph to scope
 * the View Model to the nav Graph.
 */
@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.getViewModel(
    navController: NavController
): VM {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
package `in`.iot.lab.playgame.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.playgame.view.screens.PlayHintScreenControl
import `in`.iot.lab.playgame.view.screens.PlayScannerScreenControl
import `in`.iot.lab.playgame.view.vm.PlayViewModel


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
 * @param navController This is the nav controller which would be used for this feature only.
 * @param onBackPress This function is invoked when the user hits back from the play game feature.
 */
@Composable
fun PlayGameNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: PlayViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {

    // Nav Graph for the Play Game Feature
    NavHost(
        navController = navController,
        startDestination = PLAY_GAME_SCANNER_ROUTE
    ) {

        // Scanner Screen
        composable(PLAY_GAME_SCANNER_ROUTE) {

            val scannerState = viewModel.hintData.collectAsState().value

            // Scanner Screen
            PlayScannerScreenControl(
                scannerState = scannerState,
                navigateToHints = { navController.navigate(PLAY_GAME_HINT_ROUTE) },
                popBackStack = navController::popBackStack,
                setEvent = viewModel::uiListener
            )
        }

        // Hints Screen
        composable(PLAY_GAME_HINT_ROUTE) {
            PlayHintScreenControl()
        }
    }
}
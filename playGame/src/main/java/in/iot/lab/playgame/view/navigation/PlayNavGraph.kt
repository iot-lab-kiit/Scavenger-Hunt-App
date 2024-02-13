package `in`.iot.lab.playgame.view.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import `in`.iot.lab.playgame.view.screens.PlayScannerScreenControl
import `in`.iot.lab.playgame.view.vm.PlayViewModel


// Play Game Routes
const val PLAY_GAME_ROOT_ROUTE = "play_game_root_route"


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
 */
fun NavGraphBuilder.playGameNavGraph(onCancelClick: () -> Unit) {

    // Scanner Screen
    composable(
        PLAY_GAME_ROOT_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {

        val viewModel: PlayViewModel = hiltViewModel()

        //State Variables
        val hintData = viewModel.hintData.collectAsState().value

        // Scanner Screen
        PlayScannerScreenControl(
            hintData = hintData,
            onCancelClick = onCancelClick,
            setEvent = viewModel::uiListener
        )
    }
}
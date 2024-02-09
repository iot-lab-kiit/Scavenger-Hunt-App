package `in`.iot.lab.leaderboard.view.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import `in`.iot.lab.leaderboard.view.screens.LeaderBoardScreenControl
import `in`.iot.lab.leaderboard.view.vm.LeaderBoardViewModel


// Routes
const val LEADERBOARD_ROOT_ROUTE = "leaderboard_root_route"


/**
 * This function is used to navigate to the Leader Board Screen [LeaderBoardScreenControl]
 *
 * @param navOptions This is used to provide the navigation options to navigate to this screen.
 */
fun NavController.navigateToLeaderBoard(navOptions: NavOptions? = null) {
    this.navigate(LEADERBOARD_ROOT_ROUTE, navOptions)
}


/**
 * This function is used to define the Navigation Graph of the Leader Board Feature
 *
 * @param onBackPress This function is invoked when the user presses back button in the leader
 * board screen.
 */
fun NavGraphBuilder.leaderBoardNavGraph(onBackPress: () -> Unit) {

    // Leader Board Screen
    composable(LEADERBOARD_ROOT_ROUTE) {

        // View Model
        val viewModel = hiltViewModel<LeaderBoardViewModel>()

        // State variables
        val teamList = viewModel.teamList.collectAsState().value

        // Leader Board Screen
        LeaderBoardScreenControl(
            teamList = teamList,
            setEvent = viewModel::uiListener,
            onBackPress = onBackPress
        )
    }
}
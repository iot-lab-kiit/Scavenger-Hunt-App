package `in`.iot.lab.teambuilding.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import `in`.iot.lab.teambuilding.view.TeamBuildingViewModel
import `in`.iot.lab.teambuilding.view.screens.CreateTeamScreenControl
import `in`.iot.lab.teambuilding.view.screens.RegisterTeamScreenControl
import `in`.iot.lab.teambuilding.view.screens.JoinTeamScreenControl
import `in`.iot.lab.teambuilding.view.screens.TeamHomeScreen


// Routes
const val TEAM_BUILDING_ROOT_ROUTE = "team-building-root-route"
private const val TEAM_BUILDING_HOME_ROUTE = "team-building-home-route"
private const val TEAM_BUILDING_CREATE_ROUTE = "team-building-create-route"
private const val TEAM_BUILDING_REGISTER_ROUTE = "team-building-create-qr-route"
private const val TEAM_BUILDING_JOIN_ROUTE = "team-building-join-route"


/**
 * Use this function to navigate to the Team Building Routes
 */
fun NavController.navigateToTeamBuilding(navOptions: NavOptions? = null) {
    this.navigate(TEAM_BUILDING_ROOT_ROUTE, navOptions)
}

/**
 * This is the nav Graph for the Team - Building Functionality
 *
 * @param navController This is used to navigate from screens to screens
 */
fun NavGraphBuilder.teamNavGraph(
    navController: NavController,
    onTeamRegistered: () -> Unit
) {

    // TODO: Navigate to dashboard once team building is done

    // Team Building Root Route
    navigation(
        route = TEAM_BUILDING_ROOT_ROUTE,
        startDestination = TEAM_BUILDING_HOME_ROUTE
    ) {


        // Home Routes
        composable(TEAM_BUILDING_HOME_ROUTE) {
            TeamHomeScreen(navController = navController)
        }

        // Create Screen Routes
        composable(TEAM_BUILDING_CREATE_ROUTE) {
            CreateTeamScreenControl {
                navController.navigate("$TEAM_BUILDING_REGISTER_ROUTE/$it")
            }
        }

        // Register Screen
        composable("$TEAM_BUILDING_REGISTER_ROUTE/{teamName}") {

            val vm = it.getViewModel<TeamBuildingViewModel>(navController)
            // View Model
            val viewModel = hiltViewModel<TeamBuildingViewModel>()

            // State Variables
            val teamName = it.arguments?.getString("teamName") ?: ""
            val createTeamState = viewModel.createTeamApiState.collectAsState().value

            // Team QR Generating Screen
            RegisterTeamScreenControl(
                teamName = teamName,
                createTeamState = createTeamState,
                setEvent = viewModel::uiListener
            )
        }

        // Join Screen Route
        composable(TEAM_BUILDING_JOIN_ROUTE) {

            // View Model
            val viewModel = hiltViewModel<TeamBuildingViewModel>()

            // State Variables
            val installState = viewModel.qrInstallerState.collectAsState().value
            val teamJoiningApiState = viewModel.teamJoiningApiState.collectAsState().value

            // Join Screen
            JoinTeamScreenControl(
                installState = installState,
                teamJoiningApiState = teamJoiningApiState,
                navController = navController,
                setEvent = viewModel::uiListener
            )
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
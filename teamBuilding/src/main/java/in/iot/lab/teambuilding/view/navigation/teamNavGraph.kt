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
import androidx.navigation.navOptions
import androidx.navigation.navigation
import `in`.iot.lab.teambuilding.view.vm.TeamBuildingViewModel
import `in`.iot.lab.teambuilding.view.screens.CreateTeamScreenControl
import `in`.iot.lab.teambuilding.view.screens.RegisterTeamScreenControl
import `in`.iot.lab.teambuilding.view.screens.JoinTeamScreenControl
import `in`.iot.lab.teambuilding.view.screens.TeamHomeScreenControl


// Routes
const val TEAM_BUILDING_ROOT_ROUTE = "team-building-root-route"
internal const val TEAM_BUILDING_HOME_ROUTE = "team-building-home-route"
internal const val TEAM_BUILDING_CREATE_ROUTE = "team-building-create-route"
internal const val TEAM_BUILDING_REGISTER_ROUTE = "team-building-create-qr-route"
internal const val TEAM_BUILDING_JOIN_ROUTE = "team-building-join-route"


/**
 * Use this function to navigate to the Team Building Routes
 */
fun NavController.navigateToTeamBuilding(navOptions: NavOptions? = null) {
    this.navigate(TEAM_BUILDING_ROOT_ROUTE, navOptions)
}


/**
 * This function is used to navigate to the [RegisterTeamScreenControl] screen.
 */
internal fun NavController.navigateToRegister() {
    this.navigate(TEAM_BUILDING_REGISTER_ROUTE) {
        navOptions {
            popUpTo(TEAM_BUILDING_REGISTER_ROUTE) {
                inclusive = true
            }
        }
    }
}


/**
 * This function is used to navigate to the [CreateTeamScreenControl] screen
 */
internal fun NavController.navigateToCreate() {
    this.navigate(TEAM_BUILDING_CREATE_ROUTE)
}


/**
 * This function is used to pop the Backstack from the graph
 */
internal fun NavController.navigateToJoin() {
    this.navigate(TEAM_BUILDING_JOIN_ROUTE)
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

    // Team Building Root Route
    navigation(
        route = TEAM_BUILDING_ROOT_ROUTE,
        startDestination = TEAM_BUILDING_HOME_ROUTE
    ) {


        // Home Routes
        composable(TEAM_BUILDING_HOME_ROUTE) {

            // View Model of the graph
            val viewModel = it.getViewModel<TeamBuildingViewModel>(navController)

            // User Registration State
            val userState = viewModel.teamData.collectAsState().value

            // Team Home screen
            TeamHomeScreenControl(
                userState = userState,
                navigateToRegister = navController::navigateToRegister,
                navigateToJoin = navController::navigateToJoin,
                navigateToCreate = navController::navigateToCreate,
                setEvent = viewModel::uiListener,
                onTeamRegistered = onTeamRegistered,
                onBackPress = navController::popBackStack
            )
        }

        // Create Screen Routes
        composable(TEAM_BUILDING_CREATE_ROUTE) {

            // View Model of the graph
            val viewModel = it.getViewModel<TeamBuildingViewModel>(navController)

            // Team Name and create team api call State
            val teamName = viewModel.teamName.collectAsState().value
            val createTeamState = viewModel.teamDataState.collectAsState().value

            // Create Team
            CreateTeamScreenControl(
                teamName = teamName,
                createTeamState = createTeamState,
                setEvent = viewModel::uiListener,
                onNavigateToRegistration = navController::navigateToRegister
            )
        }

        // Register Screen
        composable(TEAM_BUILDING_REGISTER_ROUTE) {

            // View Model
            val viewModel = it.getViewModel<TeamBuildingViewModel>(navController)

            // State Variables
            val createTeamState = viewModel.teamDataState.collectAsState().value

            // Team QR Generating Screen
            RegisterTeamScreenControl(
                teamDataState = createTeamState,
                setEvent = viewModel::uiListener,
                onTeamRegistered = onTeamRegistered
            )
        }

        // Join Screen Route
        composable(TEAM_BUILDING_JOIN_ROUTE) {

            // View Model
            val viewModel = hiltViewModel<TeamBuildingViewModel>()

            // State Variables
            val installState = viewModel.qrInstallerState.collectAsState().value
            val teamJoiningApiState = viewModel.teamDataState.collectAsState().value

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
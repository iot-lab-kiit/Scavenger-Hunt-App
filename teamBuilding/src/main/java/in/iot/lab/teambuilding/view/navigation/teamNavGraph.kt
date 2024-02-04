package `in`.iot.lab.teambuilding.view.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import `in`.iot.lab.teambuilding.view.TeamBuildingViewModel
import `in`.iot.lab.teambuilding.view.screens.TeamCreateScreen
import `in`.iot.lab.teambuilding.view.screens.TeamCreateQr
import `in`.iot.lab.teambuilding.view.screens.TeamJoinScreenControl
import `in`.iot.lab.teambuilding.view.screens.TeamHome


const val TEAM_BUILDING_ROUTE = "team-building-home-route"
private const val CREATE_TEAM_ROUTE = "team-building-create-route"
private const val CREATE_TEAM_QR_ROUTE = "team-building-create-qr-route"
private const val JOIN_TEAM_ROUTE = "team-building-join-route"


/**
 * Use this function to navigate to the Team Building Routes
 */
fun NavController.navigateToTeamBuilding(navOptions: NavOptions? = null) {
    this.navigate(TEAM_BUILDING_ROUTE, navOptions)
}

/**
 * This is the nav Graph for the Team - Building Functionality
 *
 * @param navController This is used to navigate from screens to screens
 */
fun NavGraphBuilder.teamNavGraph(navController: NavController) {

    // Home Routes
    composable(TEAM_BUILDING_ROUTE) {
        TeamHome(navController = navController)
    }

    // Create Screen Routes
    composable(CREATE_TEAM_ROUTE) {
        TeamCreateScreen {
            navController.navigate("$CREATE_TEAM_QR_ROUTE/$it")
        }
    }

    // QR Screen for the Team Joining Process
    composable("$CREATE_TEAM_QR_ROUTE/{teamName}") {

        // View Model
        val viewModel = hiltViewModel<TeamBuildingViewModel>()

        // State Variables
        val teamName = it.arguments?.getString("teamName") ?: ""
        val createTeamState = viewModel.createTeamApiState.collectAsState().value

        // Team QR Generating Screen
        TeamCreateQr(
            teamName = teamName,
            createTeamState = createTeamState,
            setEvent = viewModel::uiListener
        )
    }

    // Join Screen Route
    composable(JOIN_TEAM_ROUTE) {

        // View Model
        val viewModel = hiltViewModel<TeamBuildingViewModel>()

        // State Variables
        val installState = viewModel.qrInstallerState.collectAsState().value
        val teamJoiningApiState = viewModel.teamJoiningApiState.collectAsState().value

        // Join Screen
        TeamJoinScreenControl(
            installState = installState,
            teamJoiningApiState = teamJoiningApiState,
            navController = navController,
            setEvent = viewModel::uiListener
        )
    }
}
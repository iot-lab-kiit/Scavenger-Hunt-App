package `in`.iot.lab.teambuilding.view.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import `in`.iot.lab.teambuilding.view.screens.CreateScreen
import `in`.iot.lab.teambuilding.view.screens.JoinScreen
import `in`.iot.lab.teambuilding.view.screens.TeamHome

const val TEAM_BUILDING_ROUTE = "team-building-home-route"
private const val CREATE_TEAM_ROUTE = "team-building-create-route"
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
        CreateScreen(navController = navController)
    }

    // Join Screen Route
    composable(JOIN_TEAM_ROUTE) {
        JoinScreen(navController = navController)
    }
}
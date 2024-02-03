package `in`.iot.lab.teambuilding.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teambuilding.view.screens.CreateScreen
import `in`.iot.lab.teambuilding.view.screens.JoinScreen
import `in`.iot.lab.teambuilding.view.screens.TeamHome

@Composable
fun TeamNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = TeamScreens.HomeRoute.route) {

        composable(TeamScreens.HomeRoute.route) {
            TeamHome(navController = navController)
        }

        composable(TeamScreens.CreateScreenRoute.route) {
            CreateScreen(navController = navController)
        }

        composable(TeamScreens.JoinScreenRoute.route) {
            JoinScreen(navController = navController)
        }
    }
}
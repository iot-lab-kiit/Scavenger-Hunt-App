package `in`.iot.lab.scavengerhunt.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import `in`.iot.lab.teambuilding.view.navigation.TEAM_BUILDING_ROUTE
import `in`.iot.lab.teambuilding.view.navigation.teamNavGraph


/**
 * This is the Base Nav Graph which would act as the Root or parent of all the other nav Graphs
 *
 * @param navHostController This is used to navigate from screen to screen
 */
@Composable
fun MainNavGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = TEAM_BUILDING_ROUTE
    ) {

        // Team - Building Nav Graph
        teamNavGraph(navHostController)
    }
}
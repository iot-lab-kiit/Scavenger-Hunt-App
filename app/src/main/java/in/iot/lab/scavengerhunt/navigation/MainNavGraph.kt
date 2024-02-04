package `in`.iot.lab.scavengerhunt.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import `in`.iot.lab.authorization.ui.navigation.SIGNIN_ROUTE
import `in`.iot.lab.authorization.ui.navigation.signInScreen
import `in`.iot.lab.teambuilding.view.navigation.TEAM_BUILDING_ROUTE
import `in`.iot.lab.teambuilding.view.navigation.navigateToTeamBuilding
import `in`.iot.lab.teambuilding.view.navigation.teamNavGraph


/**
 * This is the Base Nav Graph which would act as the Root or parent of all the other nav Graphs
 *
 * @param navHostController This is used to navigate from screen to screen
 */
@Composable
fun MainNavGraph(navHostController: NavHostController) {
    val initialRoute =
        if (Firebase.auth.currentUser != null) TEAM_BUILDING_ROUTE
        else SIGNIN_ROUTE
    NavHost(
        navController = navHostController,
        startDestination = initialRoute
    ) {
        // Authorization Screen
        signInScreen(
            onUserSignedIn = {
                navHostController.navigateToTeamBuilding(
                    navOptions = navOptions {
                        popUpTo(SIGNIN_ROUTE) {
                            inclusive = true
                        }
                    }
                )
            }
        )
        // Team - Building Nav Graph
        teamNavGraph(navHostController)
    }
}
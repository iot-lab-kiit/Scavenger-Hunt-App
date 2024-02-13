package `in`.iot.lab.scavengerhunt.navigation

import android.app.Activity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import `in`.iot.lab.authorization.ui.navigation.SIGN_IN_ROUTE
import `in`.iot.lab.authorization.ui.navigation.signInScreen
import `in`.iot.lab.dashboard.ui.navigation.dashboardScreen
import `in`.iot.lab.dashboard.ui.navigation.navigateToDashboard
import `in`.iot.lab.scavengerhunt.screen.PermissionScreen
import `in`.iot.lab.teambuilding.view.navigation.TEAM_BUILDING_ROOT_ROUTE
import `in`.iot.lab.teambuilding.view.navigation.navigateToTeamBuilding
import `in`.iot.lab.teambuilding.view.navigation.teamNavGraph


/**
 * This is the Base Nav Graph which would act as the Root or parent of all the other nav Graphs
 *
 * @param navHostController This is used to navigate from screen to screen
 */
@Composable
fun MainNavGraph(navHostController: NavHostController) {

    val context = LocalContext.current as Activity

    PermissionScreen {
        if (!it) context.finish()
    }

    // Finding Initial Route
    val initialRoute = if (Firebase.auth.currentUser != null)
        TEAM_BUILDING_ROOT_ROUTE
    else
        SIGN_IN_ROUTE
    NavHost(
        navController = navHostController,
        startDestination = initialRoute,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {

        // Authorization Screen
        signInScreen(
            onUserSignedIn = {
                navHostController.navigateToTeamBuilding(
                    navOptions = navOptions {
                        popUpTo(SIGN_IN_ROUTE) {
                            inclusive = true
                        }
                    }
                )
            }
        )

        // Team - Building Nav Graph
        teamNavGraph(navHostController) {
            navHostController.navigateToDashboard(
                navOptions = navOptions {
                    popUpTo(TEAM_BUILDING_ROOT_ROUTE) {
                        inclusive = true
                    }
                }
            )
        }

        // using DASHBOARD_ROOT as the route
        // Dashboard Nav Graph
        dashboardScreen()
    }
}
package `in`.iot.lab.dashboard.ui.navigation

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import `in`.iot.lab.dashboard.R
import `in`.iot.lab.dashboard.ui.screen.team.TeamRoute
import `in`.iot.lab.dashboard.ui.screen.team.TeamScreenViewModel
import `in`.iot.lab.dashboard.ui.screen.team_details.TeamDetailsRoute
import `in`.iot.lab.design.animation.navigation.exit.appFadeOutTransition
import `in`.iot.lab.leaderboard.view.navigation.LEADERBOARD_ROOT_ROUTE
import `in`.iot.lab.leaderboard.view.navigation.leaderBoardNavGraph
import `in`.iot.lab.playgame.view.navigation.PLAY_GAME_ROOT_ROUTE
import `in`.iot.lab.playgame.view.navigation.PlayGameNavGraph
import `in`.iot.lab.playgame.view.navigation.navigateToPlay


const val TEAM_ROUTE = "team_route"
const val TEAM_DETAILS_ROUTE = "team_details_route"
const val CREDIT_ROUTE = "credit_route"

sealed class DashboardOptions(
    val route: String,
    @DrawableRes val icon: Int = 0,
    @DrawableRes val selectedIcon: Int = 0
) {
    data object Team : DashboardOptions(
        route = TEAM_ROUTE,
        icon = R.drawable.ic_group_outline,
        selectedIcon = R.drawable.ic_group
    )

    data object Credits: DashboardOptions(
        route = CREDIT_ROUTE,
        icon = R.drawable.ic_info_outline,
        selectedIcon = R.drawable.ic_info
    )

    data object Leaderboard : DashboardOptions(
        route = LEADERBOARD_ROOT_ROUTE,
        icon = R.drawable.ic_leaderboard_outline,
        selectedIcon = R.drawable.ic_leaderboard
    )
}

fun NavController.navigateToTeam(navOptions: NavOptions) =
    navigate(DashboardOptions.Team.route, navOptions)

fun NavController.navigateToTeamDetails(navOptions: NavOptions? = null) =
    navigate(TEAM_DETAILS_ROUTE, navOptions)


@Composable
internal fun DashboardNavGraph(
    navController: NavHostController,
    teamViewModel: TeamScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current as Activity
    val onBackPressed: () -> Unit = {
        with(navController) {
            if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                popBackStack()
            }
        }
    }
    NavHost(
        navController = navController,
        route = DASHBOARD_ROOT,
        startDestination = DashboardOptions.Team.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { appFadeOutTransition() },
        popEnterTransition = { EnterTransition.None }
    ) {

        // Team Screen
        composable(DashboardOptions.Team.route) {
            TeamRoute(
                viewModel = teamViewModel,
                onCancelClick = { context.finish() },
                onTryAgainClick = teamViewModel::getTeamByUserUid,
                onNavigateToTeamDetails = navController::navigateToTeamDetails,
                onNavigateToPlay = {
                    navController.navigateToPlay(navOptions {})
                }
            )
        }

        // Team Details Screen
        composable(TEAM_DETAILS_ROUTE) {
            TeamDetailsRoute(
                viewModel = teamViewModel,
                onBackClick = onBackPressed,
                onTryAgainClick = teamViewModel::getTeamByUserUid,
                onCancelClick = { context.finish() }
            )
        }

        // Play Game Screen
        composable(PLAY_GAME_ROOT_ROUTE) {
            PlayGameNavGraph(
                onCancelClick = {
                    navController.navigateToTeam(navOptions {})
                }
            )
        }

        composable(DashboardOptions.Credits.route) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Credits")
            }
        }

        //

        // Leaderboard Screen
        leaderBoardNavGraph { }
    }
}
package `in`.iot.lab.dashboard.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import `in`.iot.lab.dashboard.R
import `in`.iot.lab.dashboard.ui.screen.leaderboard.LeaderboardScreen
import `in`.iot.lab.dashboard.ui.screen.play.PlayScreen
import `in`.iot.lab.dashboard.ui.screen.team.TeamRoute
import `in`.iot.lab.dashboard.ui.screen.team.TeamScreenViewModel
import `in`.iot.lab.dashboard.ui.screen.team_details.TeamDetailsRoute


const val TEAM_ROUTE = "team_route"
const val TEAM_DETAILS_ROUTE = "team_details_route"
const val PLAY_ROUTE = "play_route"
const val LEADERBOARD_ROUTE = "leaderboard_route"


sealed class DashboardOptions(
    val route: String,
    @DrawableRes val icon: Int = 0,
    @DrawableRes val selectedIcon: Int = 0
) {
    data object Team : DashboardOptions(
        route = TEAM_ROUTE,
        icon = R.drawable.ic_group_outline,
        selectedIcon = R.drawable.ic_group
    ) {
        // Was testing something
        data object TeamDetails : DashboardOptions(
            route = TEAM_DETAILS_ROUTE
        )
    }

    data object Play : DashboardOptions(
        route = PLAY_ROUTE,
        icon = R.drawable.ic_home_outline,
        selectedIcon = R.drawable.ic_home
    )

    data object Leaderboard : DashboardOptions(
        route = LEADERBOARD_ROUTE,
        icon = R.drawable.ic_leaderboard_outline,
        selectedIcon = R.drawable.ic_leaderboard
    )
}

fun NavController.navigateToTeam(navOptions: NavOptions) =
    navigate(DashboardOptions.Team.route, navOptions)

fun NavController.navigateToTeamDetails(navOptions: NavOptions) =
    navigate(DashboardOptions.Team.TeamDetails.route, navOptions)

fun NavController.navigateToPlay(navOptions: NavOptions) =
    navigate(DashboardOptions.Play.route, navOptions)

fun NavController.navigateToLeaderboard(navOptions: NavOptions) =
    navigate(DashboardOptions.Leaderboard.route, navOptions)

@Composable
internal fun DashboardNavGraph(
    navController: NavHostController,
    teamViewModel: TeamScreenViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        route = DASHBOARD_ROOT,
        startDestination = DashboardOptions.Team.route
    ) {
        composable(DashboardOptions.Team.route) {
            TeamRoute(
                viewModel = teamViewModel,
                onNavigateToTeamDetails = { navController.navigateToTeamDetails(navOptions = navOptions { }) }
            )
        }
        composable(DashboardOptions.Team.TeamDetails.route) {
            TeamDetailsRoute(
                viewModel = teamViewModel,
                onBackClick = navController::popBackStack
            )
        }
        composable(DashboardOptions.Play.route) {
            PlayScreen()
        }
        composable(DashboardOptions.Leaderboard.route) {
            LeaderboardScreen()
        }
    }
}
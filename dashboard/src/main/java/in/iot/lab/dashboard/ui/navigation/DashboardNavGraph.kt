package `in`.iot.lab.dashboard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import `in`.iot.lab.dashboard.ui.screen.leaderboard.LeaderboardScreen
import `in`.iot.lab.dashboard.ui.screen.play.PlayScreen
import `in`.iot.lab.dashboard.ui.screen.team.TeamRoute
import `in`.iot.lab.dashboard.ui.screen.team.TeamScreenViewModel
import `in`.iot.lab.dashboard.ui.screen.team_details.TeamDetailsRoute

@Composable
internal fun DashboardNavGraph(
    navController: NavHostController,
    teamViewModel: TeamScreenViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        route = DASHBOARD_ROOT,
        startDestination = DashboardRoutes.Team.route
    ) {
        composable(DashboardRoutes.Team.route) {
            TeamRoute(
                viewModel = teamViewModel,
                onNavigateToTeamDetails = {
                    navController.navigate(TEAM_DETAILS_ROUTE)
                }
            )
        }
        composable(TEAM_DETAILS_ROUTE) {
            TeamDetailsRoute(
                viewModel = teamViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(DashboardRoutes.Play.route) {
            PlayScreen()
        }
        composable(DashboardRoutes.Leaderboard.route) {
            LeaderboardScreen()
        }
    }
}
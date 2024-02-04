package `in`.iot.lab.dashboard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import `in`.iot.lab.dashboard.ui.screen.leaderboard.LeaderboardScreen
import `in`.iot.lab.dashboard.ui.screen.play.PlayScreen
import `in`.iot.lab.dashboard.ui.screen.team.TeamScreen

@Composable
internal fun DashboardNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = DASHBOARD_ROOT,
        startDestination = DashboardRoutes.TEAM_ROUTE
    ) {
        composable(DashboardRoutes.TEAM_ROUTE) {
            TeamScreen()
        }
        composable(DashboardRoutes.PLAY_ROUTE) {
            PlayScreen()
        }
        composable(DashboardRoutes.LEADERBOARD_ROUTE) {
            LeaderboardScreen()
        }
    }
}
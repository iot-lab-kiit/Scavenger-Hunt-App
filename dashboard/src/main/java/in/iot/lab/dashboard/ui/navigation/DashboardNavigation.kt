package `in`.iot.lab.dashboard.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import `in`.iot.lab.dashboard.ui.screen.DashboardScreen

const val DASHBOARD_ROOT = "dashboard_route"
internal object DashboardRoutes {
    const val TEAM_ROUTE = "team_route"
    const val PLAY_ROUTE = "play_route"
    const val LEADERBOARD_ROUTE = "leaderboard_route"
}

fun NavController.navigateToDashboard(navOptions: NavOptions) = navigate(DASHBOARD_ROOT, navOptions)

fun NavGraphBuilder.dashboardScreen() {
    composable(DASHBOARD_ROOT) {
        DashboardScreen()
    }
}
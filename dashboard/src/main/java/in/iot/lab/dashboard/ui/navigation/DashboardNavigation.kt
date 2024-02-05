package `in`.iot.lab.dashboard.ui.navigation

import androidx.annotation.DrawableRes
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import `in`.iot.lab.dashboard.R
import `in`.iot.lab.dashboard.ui.screen.DashboardScreen

const val DASHBOARD_ROOT = "dashboard_route"

sealed class DashboardRoutes(
    val route: String,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int
) {
    data object Team : DashboardRoutes(
        route = "team_route",
        icon = R.drawable.ic_group_outline,
        selectedIcon = R.drawable.ic_group
    )
    data object Play : DashboardRoutes(
        route = "play_route",
        icon = R.drawable.ic_home_outline,
        selectedIcon = R.drawable.ic_home
    )
    data object Leaderboard : DashboardRoutes(
        route = "leaderboard_route",
        icon = R.drawable.ic_leaderboard_outline,
        selectedIcon = R.drawable.ic_leaderboard
    )
}

fun NavController.navigateToDashboard(navOptions: NavOptions) = navigate(DASHBOARD_ROOT, navOptions)

fun NavGraphBuilder.dashboardScreen() {
    composable(DASHBOARD_ROOT) {
        DashboardScreen()
    }
}
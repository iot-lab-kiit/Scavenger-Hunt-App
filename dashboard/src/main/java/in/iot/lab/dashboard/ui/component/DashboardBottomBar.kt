package `in`.iot.lab.dashboard.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import `in`.iot.lab.dashboard.ui.navigation.DashboardRoutes

@Composable
internal fun DashboardBottomBar(navController: NavController) {
    val screens = listOf(
        DashboardRoutes.TEAM_ROUTE,
        DashboardRoutes.PLAY_ROUTE,
        DashboardRoutes.LEADERBOARD_ROUTE
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar {
            screens.forEach { screen ->
                NavigationBarItem(
                    selected = currentDestination?.route == screen,
                    onClick = { navController.navigate(screen) },
                    icon = {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                    },
                    label = {
                        Text(text = screen)
                    }
                )
            }
        }
    }
}
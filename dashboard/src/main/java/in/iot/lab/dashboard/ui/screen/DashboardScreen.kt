package `in`.iot.lab.dashboard.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.dashboard.ui.component.DashboardBottomBar
import `in`.iot.lab.dashboard.ui.navigation.DashboardNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun DashboardScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { DashboardBottomBar(navController) }
    ) {
        DashboardNavGraph(navController)
    }
}
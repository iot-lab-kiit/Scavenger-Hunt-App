package `in`.iot.lab.dashboard.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.dashboard.ui.component.DashboardBottomBar
import `in`.iot.lab.dashboard.ui.navigation.DashboardNavGraph
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.theme.ScavengerHuntTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun DashboardScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { DashboardBottomBar(navController) }
    ) {
        DashboardNavGraph(navController)
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    ScavengerHuntTheme {
        AppScreen {
            DashboardScreen()
        }
    }
}
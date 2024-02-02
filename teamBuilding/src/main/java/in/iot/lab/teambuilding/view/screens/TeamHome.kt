package `in`.iot.lab.teambuilding.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import `in`.iot.lab.teambuilding.view.navigation.TeamScreens

@Composable
fun TeamHome(navController: NavController) {

    Column {
        Button(onClick = { navController.navigate(TeamScreens.CreateScreenRoute.route) }) {
            Text(text = "Create Team")
        }
        Button(onClick = { navController.navigate(TeamScreens.JoinScreenRoute.route) }) {
            Text(text = "Join Team")
        }

    }


}
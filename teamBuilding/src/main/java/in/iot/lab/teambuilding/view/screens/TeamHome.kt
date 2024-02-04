package `in`.iot.lab.teambuilding.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.teambuilding.R
import `in`.iot.lab.teambuilding.view.components.AppScreen
import `in`.iot.lab.teambuilding.view.components.TeamBuildingButton
import `in`.iot.lab.teambuilding.view.components.TheMatrixHeaderUI

// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    ScavengerHuntTheme {
        TeamHome(rememberNavController())
    }
}


@Composable
fun TeamHome(navController: NavController) {

    // Background Related Customizations
    AppScreen {

        // Background Image
        Image(
            painter = painterResource(id = R.drawable.team_building_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Parent Composable
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            // Matrix and Scavenger Hunt Image Header
            TheMatrixHeaderUI()

            Spacer(modifier = Modifier.height(150.dp))

            // Create Team Button
            TeamBuildingButton(buttonLabel = "CREATE TEAM") {
                navController.navigate("team-building-create-route")
            }

            // Join Team Button
            TeamBuildingButton(
                buttonLabel = "JOIN TEAM",
                buttonColor = ButtonDefaults.buttonColors(containerColor = Color(0xFFCC2936)),
                textColor = Color.White
            ) {
                navController.navigate("team-building-join-route")
            }
        }
    }
}
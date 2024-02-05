package `in`.iot.lab.teambuilding.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.TheMatrixHeaderUI
import `in`.iot.lab.teambuilding.view.components.TeamBuildingOutlinedTextField

@Composable
internal fun CreateTeamScreenControl(onNavigateToQRScreen: (String) -> Unit) {

    // Team Name Input from the User
    var teamName by remember { mutableStateOf("") }

    // Default Background
    AppScreen {

        // Parent Composable
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Matrix and Scavenger Hunt Image Header
            TheMatrixHeaderUI()

            Spacer(modifier = Modifier.height(150.dp))

            // Outlined Text Field for inputting team name
            TeamBuildingOutlinedTextField(
                modifier = Modifier.padding(horizontal = 32.dp),
                input = teamName,
                onValueChange = {
                    teamName = it
                },
                labelString = "Team Name"
            )

            // Create Team Button
            PrimaryButton(
                onClick = { onNavigateToQRScreen(teamName) },
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth()
                    .height(height = 56.dp)
            ) {
                Text(text = "Create Team")
            }
        }
    }
}
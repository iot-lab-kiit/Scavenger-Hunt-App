package `in`.iot.lab.teambuilding.view.screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import `in`.iot.lab.design.components.AppBackgroundImage
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.SecondaryButton
import `in`.iot.lab.design.components.TheMatrixHeaderUI
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.teambuilding.view.components.ConfirmDialogUI
import `in`.iot.lab.teambuilding.view.events.TeamBuildingEvent
import `in`.iot.lab.teambuilding.view.state.UserRegistrationState

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
        AppScreen { TeamHomeNotRegisteredScreen({}, {}) }
    }
}

// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview2() {
    ScavengerHuntTheme {
        AppScreen {
            TeamHomeScreenControl(
                navigateToCreate = {},
                navigateToJoin = {},
                navigateToRegister = {},
                userState = UserRegistrationState.Error("Failed to find User"),
                setEvent = {},
                onTeamRegistered = {},
                onBackPress = {}
            )
        }
    }
}


@Composable
internal fun TeamHomeScreenControl(
    userState: UserRegistrationState,
    navigateToCreate: () -> Unit,
    navigateToJoin: () -> Unit,
    navigateToRegister: () -> Unit,
    onBackPress: () -> Unit,
    setEvent: (TeamBuildingEvent) -> Unit,
    onTeamRegistered: () -> Unit
) {

    AppScreen {

        // Checking User state and taking decision accordingly
        when (userState) {

            // Idle State
            is UserRegistrationState.Idle -> {
                setEvent(TeamBuildingEvent.NetworkIO.GetUserRegistrationData)
            }

            // Loading State
            is UserRegistrationState.Loading -> {
                LoadingTransition()
            }

            // Not Registered and not in Team State
            is UserRegistrationState.NotRegistered -> {
                TeamHomeNotRegisteredScreen(
                    navigateToCreate = navigateToCreate,
                    navigateToJoin = navigateToJoin
                )
            }

            // In a Team and not Registered State
            is UserRegistrationState.InTeam -> {
                navigateToRegister()
            }

            // User Team is Registered State
            is UserRegistrationState.Registered -> {
                onTeamRegistered()
            }

            // Error State
            is UserRegistrationState.Error -> {
                ErrorDialog(
                    text = userState.message,
                    onCancel = onBackPress,
                    onTryAgain = {
                        setEvent(TeamBuildingEvent.NetworkIO.GetUserRegistrationData)
                    }
                )
            }
        }
    }
}


@Composable
private fun TeamHomeNotRegisteredScreen(
    navigateToCreate: () -> Unit,
    navigateToJoin: () -> Unit
) {

    var isJoinLast by remember { mutableStateOf(false) }

    // Background Image
    AppBackgroundImage()

    AnimatedVisibility(visible = isJoinLast) {
        Dialog(onDismissRequest = { isJoinLast = false }) {

            ConfirmDialogUI(
                text = "Are you sure you want to continue? You won't be able to" +
                        " join another team after joining this one.",
                onDismiss = { isJoinLast = false },
                onContinue = navigateToJoin
            )
        }
    }

    // Parent Composable
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        // Matrix and Scavenger Hunt Image Header
        TheMatrixHeaderUI()

        // Create Team Button
        SecondaryButton(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .height(height = 56.dp),
            onClick = navigateToCreate,
        ) {
            Text(text = "CREATE TEAM")
        }


        // Join Team Button
        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .height(height = 56.dp),
            onClick = {
                isJoinLast = true
            }
        ) {
            Text(text = "JOIN TEAM")
        }
    }
}
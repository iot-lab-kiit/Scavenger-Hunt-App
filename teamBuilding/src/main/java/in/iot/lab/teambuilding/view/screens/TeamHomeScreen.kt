package `in`.iot.lab.teambuilding.view.screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.SecondaryButton
import `in`.iot.lab.design.components.TheMatrixHeaderUI
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.network.data.models.team.RemoteTeam
import `in`.iot.lab.network.data.models.user.RemoteUser
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
        TeamHomeNotRegisteredScreen({}, {})
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


@Composable
internal fun TeamHomeScreenControl(
    userState: UserRegistrationState<RemoteTeam>,
    navigateToCreate: () -> Unit,
    navigateToJoin: () -> Unit,
    navigateToRegister: () -> Unit,
    onBackPress: () -> Unit,
    setEvent: (TeamBuildingEvent) -> Unit,
    onTeamRegistered: () -> Unit
) {

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


@Composable
private fun TeamHomeNotRegisteredScreen(
    navigateToCreate: () -> Unit,
    navigateToJoin: () -> Unit
) {

    var isJoinLast by remember { mutableStateOf(false) }

    // Background Related Customizations
    AppScreen {

        // Background Image
        Image(
            painter = painterResource(id = R.drawable.matrix_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        AnimatedVisibility(visible = isJoinLast) {
            Dialog(
                onDismissRequest = {
                    isJoinLast = false
                }
            ) {
                ConfirmDialogUI(
                    text = "Are you Sure you want to Continue? You won't be able to Join " +
                            "Another Team after Joining one.",
                    imageId = R.drawable.server_error,
                    onDismiss = {
                        isJoinLast = false
                    },
                    onContinue = navigateToJoin
                )
            }
        }

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
            SecondaryButton(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth()
                    .height(height = 56.dp),
                onClick = navigateToCreate,
            ) {
                Text(text = "CREATE TEAM")
            }

            // Join Team Button
            PrimaryButton(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
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
}
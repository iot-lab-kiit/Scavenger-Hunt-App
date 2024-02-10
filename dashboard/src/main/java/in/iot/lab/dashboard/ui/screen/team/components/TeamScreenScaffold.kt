package `in`.iot.lab.dashboard.ui.screen.team.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.AppTopBar


/**
 * This function is the custom scaffold used in the Team Screen of the dashboard.
 *
 * @param headerText This is the text that would be shown as the main title of the screen
 * @param onNavigateToTeamDetails This is the function that would be invoked if the user taps on the
 * next button.
 * @param point These are the points that are shown in the UI.
 * @param body This is the body composable which would be shown in the body of this scaffold
 */
@Composable
fun TeamScreenScaffoldUI(
    headerText: String,
    onNavigateToTeamDetails: () -> Unit,
    point: String,
    body: @Composable BoxScope.() -> Unit
) {

    // Scaffold
    AppScreen(

        // Top App Bar UI
        topBar = {
            AppTopBar(
                headerText = headerText,
                onNextButtonClick = onNavigateToTeamDetails,
                pointDisplay = point
            )
        },

        contentAlignment = Alignment.TopCenter,
        body = body
    )
}
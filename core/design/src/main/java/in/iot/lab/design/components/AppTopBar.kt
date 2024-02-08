package `in`.iot.lab.design.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.theme.ScavengerHuntTheme


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
        AppTopBar(
            headerText = "HEADER STRING FILES",
            pointDisplay = "540"
        ) {}
    }
}


/**
 * This function is used to draw a top app bar with a text and a back button in the Top with the
 * default colors for the App Theme.
 *
 * @param modifier Optional parameters that can be passed from the parent function for extra
 * customization.
 * @param headerText This is the header text that will be shown in the top app bar.
 * @param onNextButtonClick This function is invoked when the next button is clicked. Note that this
 * button will only be available on the UI when the value of this variable is not null
 * @param pointDisplay This is a string variable which takes a string to denote the points
 * of a particular team. If the values is null then the UI will not be shown otherwise the points
 * UI will be shown.
 * @param onBackPress This function is invoked when the Back button is pressed. The back button will
 * only be shown when the on Back Press is not null.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    headerText: String,
    onNextButtonClick: (() -> Unit)? = null,
    pointDisplay: String? = null,
    onBackPress: (() -> Unit)? = null
) {

    // Top App Bar
    TopAppBar(
        title = {

            // Parent Composable
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // Header Text with the Next Button
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    // Header Text composable
                    AppHeaderText(text = headerText)

                    // Next Button
                    onNextButtonClick?.let {
                        NextButton(onClick = it)
                    }
                }

                // If the point is given then this composable is shown otherwise its not shown
                pointDisplay?.let {

                    // Score Points
                    AppPointsUI(text = it)
                }
            }
        },

        // Colors for the Top App Bar
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary
        ),

        // Navigation Icon which will only be shown when the onBackPress function is not null
        navigationIcon = {
            onBackPress?.let {
                IconButton(onClick = it) {

                    // Back Button
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Button",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    )
}
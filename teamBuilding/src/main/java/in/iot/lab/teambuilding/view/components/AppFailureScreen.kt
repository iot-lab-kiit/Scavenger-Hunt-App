package `in`.iot.lab.teambuilding.view.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import `in`.iot.lab.teambuilding.R


// Default Preview Function
@Preview(
    "Light",
    showBackground = true
)
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppScreen {
        AppFailureScreen()
    }
}


/**
 * This composable function is used to show the Error Screen to the User when there is a Internet
 * Issues or error during the Backend API Calls
 *
 *
 * @param modifier This is to pass modifications from the Parent Composable to the Child
 * @param title This is the Heading of the issue/error which would be shown as a Heading below
 * the Image
 * @param text This is the description of the Issue/Error which would be shown as a
 * description below the [title]
 * @param imageId This is the Image Id which would be shown in the Dialog
 * @param onTryAgain This function would be executed when the retry button would be clicked
 */
@Composable
fun AppFailureScreen(
    modifier: Modifier = Modifier,
    title: String = "Whoops !!",
    text: String = "Second Internet connection was found. Check your connection or try again.",
    imageId: Int = R.drawable.server_error,
    onCancel: () -> Unit = {},
    onTryAgain: () -> Unit = {}
) {

    // This variable says if the dialog is Visible or not
    var isDialogVisible by remember { mutableStateOf(true) }

    // We are animating the entry and Exit of the Dialog Bars
    AnimatedVisibility(isDialogVisible) {
        Dialog(
            onDismissRequest = {
                isDialogVisible = false
                onCancel()
            },

            // Defines that back press and clicking outside won't remove the Dialog
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {

            // Dialog Contents which would be shown inside the Dialog
            Card(shape = RoundedCornerShape(8.dp)) {
                DialogContent(
                    modifier = modifier,
                    imageId = imageId,
                    title = title,
                    text = text,
                    onTryAgain = onTryAgain
                ) {
                    isDialogVisible = false
                    onCancel()
                }
            }
        }
    }
}


/**
 * This function provides the Contents inside the [AppFailureScreen] Composable
 *
 * @param modifier This is for the parent function to pass modifications to the child
 * @param title This is the Heading of the issue/error which would be shown as a Heading below
 * the Image
 * @param text This is the description of the Issue/Error which would be shown as a
 * description below the [title]
 * @param imageId This is the Image Id which would be shown in the Dialog
 * @param onTryAgain This function would be executed when the retry button would be clicked
 * @param onDismiss This function would be called when the user hits the dismiss Button and it would
 * remove the Dialog from the Screen
 */
@Composable
private fun DialogContent(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    @DrawableRes imageId: Int,
    onTryAgain: () -> Unit,
    onDismiss: () -> Unit
) {

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Local Image
        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
        )


        // Issues Heading Text
        Text(
            text = title,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )


        // Issues Description Text
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
        )


        // Row containing Cancel and Try Again Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Cancel Button
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1F),
                shape = RoundedCornerShape(8.dp),
            ) {

                Text(
                    text = "Cancel",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }


            // Try Again Button
            FilledTonalButton(
                onClick = onTryAgain,
                modifier = Modifier.weight(1F),
                shape = RoundedCornerShape(8.dp),
            ) {

                Text(
                    text = "Try Again",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            }
        }
    }
}
package `in`.iot.lab.teambuilding.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp


/**
 * This function is the Default [OutlinedTextField] which would be used to take input of texts from
 * the User.
 *
 * @param modifier Optional Modifier that can be passed from the parent Function
 * @param input This is the input from the User
 * @param labelString This is the label that would be shown in the [OutlinedTextField]
 * @param colors This is the color scheme for the [OutlinedTextField]
 * @param onValueChange This function is invoked if the user inputs something
 */
@Composable
internal fun TeamBuildingOutlinedTextField(
    modifier: Modifier = Modifier,
    input: String,
    labelString: String,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = MaterialTheme.colorScheme.primary,
        focusedTextColor = MaterialTheme.colorScheme.onPrimary
    ),
    onValueChange: (String) -> Unit
) {

    // Focus Manager Object
    val focusManager = LocalFocusManager.current

    // Outlined Text Field
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        value = input,
        onValueChange = onValueChange,
        colors = colors,
        shape = RoundedCornerShape(24.dp),
        label = {

            // Label String
            Text(text = labelString)
        },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {

                // Removing the Keyboard from the Screen and removing focus from Text Field
                focusManager.clearFocus()
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        )
    )
}
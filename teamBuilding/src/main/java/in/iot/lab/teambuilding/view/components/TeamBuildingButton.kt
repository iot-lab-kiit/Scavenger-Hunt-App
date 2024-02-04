package `in`.iot.lab.teambuilding.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * This function is the default Design for a Button In the App.
 *
 * @param modifier Optional Modifier which can be passed from the Parent Composable
 * @param buttonLabel String Value to be shown in the Button
 * @param buttonColor This is the [ButtonColors] for the Button
 * @param textColor This is the Text Color in which the text is shown
 * @param onClick This function is invoked when the Button is clicked
 */
@Composable
internal fun TeamBuildingButton(
    modifier: Modifier = Modifier,
    buttonLabel: String,
    buttonColor: ButtonColors = ButtonDefaults.buttonColors(containerColor = Color(0xfff1f2eb)),
    textColor: Color = Color(0xff11151c),
    onClick: () -> Unit
) {

    // Button
    Button(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 32.dp)
            .fillMaxWidth()
            .height(height = 56.dp),
        onClick = onClick,
        colors = buttonColor,
        shape = RoundedCornerShape(24.dp)
    ) {

        // Text
        Text(
            text = buttonLabel,
            color = textColor,
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 20.sp)
        )
    }
}

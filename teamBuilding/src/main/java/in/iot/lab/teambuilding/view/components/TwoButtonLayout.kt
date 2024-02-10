package `in`.iot.lab.teambuilding.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.SecondaryButton
import `in`.iot.lab.teambuilding.R
import kotlinx.coroutines.delay


@Composable
fun TwoButtonLayout(
    primaryButtonEnable: Boolean,
    onRegisterClick: () -> Unit,
    onReloadClick: () -> Unit
) {

    var timer by remember { mutableIntStateOf(10) }
    LaunchedEffect(timer) {
        while (timer > 0) {
            delay(1000L)
            timer--
        }
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Register Button
        PrimaryButton(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 16.dp),
            enabled = primaryButtonEnable,
            onClick = onRegisterClick
        ) {
            Text(
                text = "REGISTER",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.montserratsemibold))
            )
        }

        // Reload Button
        SecondaryButton(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 16.dp),
            onClick = {
                timer = 10
                onReloadClick()
            },
            enabled = timer == 0
        ) {
            Text(
                text = "RELOAD (${timer}s)",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.montserratsemibold))
            )
        }
    }
}
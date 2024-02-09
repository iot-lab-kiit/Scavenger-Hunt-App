package `in`.iot.lab.teambuilding.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.SecondaryButton


@Composable
fun TwoButtonLayout(
    onRegisterClick: () -> Unit,
    onReloadClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Register Button
        PrimaryButton(
            modifier = Modifier.weight(1f),
            enabled = true,
            onClick = onRegisterClick
        ) {
            Text(text = "REGISTER")
        }

        // Reload Button
        SecondaryButton(
            modifier = Modifier.weight(1f),
            onClick = onReloadClick
        ) {
            Text(text = "Reload")
        }
    }
}
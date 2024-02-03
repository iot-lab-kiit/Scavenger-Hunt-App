package `in`.iot.lab.teambuilding.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


/**
 * This function is used to Give the Default Background Colors and Alignment
 *
 * @param modifier (Optional) Modifier can be passed from the parent function to this
 * @param body This is the Composable that should be inside this
 */
@Composable
internal fun AppScreen(
    modifier: Modifier = Modifier,
    body: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(Color(0xff11151c))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        body()
    }
}
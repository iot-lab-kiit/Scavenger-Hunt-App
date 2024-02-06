package `in`.iot.lab.design.components

import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingTransition(modifier: Modifier = Modifier) {
    val a = rememberInfiniteTransition()
    ThemedProgressBar(progress = .5f, modifier = modifier.fillMaxWidth(), colorScheme = RedProgressTheme)

}

@Preview
@Composable
fun Preview(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        LoadingTransition(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(Color.Black)
        )
    }
}
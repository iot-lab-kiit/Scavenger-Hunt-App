package `in`.iot.lab.leaderboard.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import `in`.iot.lab.leaderboard.R


@Preview
@Composable
fun Background() {
    Image(painter = painterResource(id = R.drawable.matrix_background),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF11151C)),
        contentScale = ContentScale.FillBounds)
}
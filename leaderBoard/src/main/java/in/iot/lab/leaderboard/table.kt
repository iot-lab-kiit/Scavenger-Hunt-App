package `in`.iot.lab.leaderboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Table(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 285.dp)
            .requiredHeight(height = 267.dp)
    ) {
        Box(
            modifier = Modifier
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 55.dp)
                .clip(shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .border(border = BorderStroke(2.dp, Color(0xffcc2936)),
                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)))
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 0.dp,
                    y = 53.dp)
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 55.dp)
                .border(border = BorderStroke(2.dp, Color(0xffcc2936))))
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 0.dp,
                    y = 106.dp)
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 55.dp)
                .border(border = BorderStroke(2.dp, Color(0xffcc2936))))
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 0.dp,
                    y = 159.dp)
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 55.dp)
                .border(border = BorderStroke(2.dp, Color(0xffcc2936))))
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 0.dp,
                    y = 212.dp)
                .requiredWidth(width = 285.dp)
                .requiredHeight(height = 55.dp)
                .clip(shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .border(border = BorderStroke(2.dp, Color(0xffcc2936)),
                    shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)))
    }
}

@Preview(widthDp = 285, heightDp = 267)
@Composable
private fun TablePreview() {
    Table(Modifier)
}
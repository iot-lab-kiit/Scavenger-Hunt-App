package `in`.iot.lab.leaderboard.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.leaderboard.data.local.Dummy
import `in`.iot.lab.leaderboard.data.local.getDummy

@Preview
@Composable
fun LdrBoardCard(dummy: Dummy= getDummy()[0]) {
    ThemeCard(text1 = dummy.teamName,
        text2 = dummy.score.toString(),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = CardDefaults.cardElevation(6.dp),
        border = BorderStroke(1.dp, Color(0xFFF1F2EB)))
}
package `in`.iot.lab.leaderboard.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.leaderboard.data.local.Dummy
import `in`.iot.lab.leaderboard.data.local.getDummy
import `in`.iot.lab.leaderboard.view.components.Background
import `in`.iot.lab.leaderboard.view.components.HeaderText
import `in`.iot.lab.leaderboard.view.components.LdrBoardCard
import `in`.iot.lab.leaderboard.view.components.ThemeCard

@Preview
@Composable
fun LdrBoardScreen(
    dummyList:List<Dummy> = getDummy()
) {

    Background()

    Column {
        HeaderText(
            text = "LEADERBOARD",
            modifier = Modifier
                .padding(top = 78.dp, start = 42.dp)
                .height(32.dp)
                .requiredWidth(240.dp)
        )

        Divider(Modifier.padding(70.dp), color = Color(0xFF11151C))

        ThemeCard(text1 = "Team Name",
            text2 = "Points",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, bottom = 10.dp),
            shape = RoundedCornerShape(corner = CornerSize(16.dp)),
            elevation = CardDefaults.cardElevation(6.dp),
            border = BorderStroke(1.dp, Color(0xFFF1F2EB))
        )

        LazyColumn(
            modifier = Modifier.padding(
                start = 32.dp,
                end = 32.dp,
                bottom = 80.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items = dummyList) {
                LdrBoardCard(dummy = it)
            }
        }

    }
}
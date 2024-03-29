package `in`.iot.lab.dashboard.ui.screen.team


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.iot.lab.dashboard.ui.screen.team.components.TextAndProgressUI
import `in`.iot.lab.dashboard.ui.screen.team.components.VanillaProgressAnimation
import `in`.iot.lab.network.data.models.team.RemoteTeam


@Composable
fun TeamScreenContent(team: RemoteTeam) {

    Column(
        modifier = Modifier.padding(horizontal = 36.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // Progress Animation (Comet Design)
        VanillaProgressAnimation(
            mainQuestsDone = team.numMain ?: 0,
            totalMain = team.totalMain,
            sideQuestDone = team.numSide ?: 0,
            totalSide = team.numSide ?: 0,
            Modifier
                .fillMaxWidth()
                .aspectRatio(1.25f),
        )

        // Spacer between the Comet Animation and the Quest Progress Bars.
        Spacer(modifier = Modifier.height(16.dp))

        // Main Quest Text and Progress
        TextAndProgressUI(
            text = "Main Quest",
            progress = team.numMain?.div(team.totalMain.toFloat()) ?: .8f
        )

        // Side Quest
        TextAndProgressUI(
            text = "Side Quest",
            progress = team.numSide?.div(team.totalSide.toFloat()) ?: .5f
        )
    }
}
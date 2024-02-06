package `in`.iot.lab.dashboard.ui.screen.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.dashboard.ui.screen.team.components.ProgressAnimation
import `in`.iot.lab.design.components.RedProgressTheme
import `in`.iot.lab.dashboard.ui.screen.team.components.TeamTopBar
import `in`.iot.lab.design.components.ThemedProgressBar
import `in`.iot.lab.design.R
import `in`.iot.lab.network.data.models.team.RemoteTeam

@Composable
fun TeamScreenContent(
    modifier: Modifier,
    team: RemoteTeam,
    mainQuests: List<Boolean>,
    sideQuests: List<Boolean>,
    onNavigateToTeamDetails: () -> Unit
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.matrix_background),
            contentDescription = "Bottom",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            TeamTopBar(
                teamName = team.teamName ?: "Team Name",
                teamScore = team.score ?: 0,
                onNavigateToTeamDetails = onNavigateToTeamDetails
            )
            Spacer(modifier = Modifier.size(40.dp))
            ProgressAnimation(
                mainQuests, sideQuests,
                Modifier
                    .fillMaxWidth(0.7f)
                    .aspectRatio(1f)
            )
            Column(
                modifier = Modifier.padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val textStyle = TextStyle(
                    color = Color.White, fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.orbitron_regular))
                )
                Text(text = "Main Quest", style = textStyle)
                val mainProgress = mainQuests.map { if (it) 1f else 0f }.average().toFloat()
                ThemedProgressBar(
                    progress = mainProgress,
                    modifier = Modifier.fillMaxWidth(),
                    RedProgressTheme
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "Side Quest", style = textStyle)
                val sideProgress = sideQuests.map { if (it) 1f else 0f }.average().toFloat()
                ThemedProgressBar(
                    progress = sideProgress,
                    modifier = Modifier.fillMaxWidth(),
                    RedProgressTheme
                )
            }
        }
    }
}
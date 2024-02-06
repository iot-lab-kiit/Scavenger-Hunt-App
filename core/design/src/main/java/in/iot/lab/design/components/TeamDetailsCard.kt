package `in`.iot.lab.design.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.theme.ScavengerHuntTheme

data class TeamMember(
    val name: String? = null,
    val isLead: Boolean? = false
)
@Composable
fun TeamDetailsCard(
    modifier: Modifier = Modifier,
    teamMember: List<TeamMember>? = null,
) {
    OutlinedCard(
        modifier = modifier.padding(25.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        border = BorderStroke(
            width = 3.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        teamMember?.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = it.name ?: "",
                    modifier = Modifier.weight(9f)
                )
                if (it.isLead == true) {
                    Image(
                        modifier = Modifier.weight(1f),
                        painter = painterResource(id = `in`.iot.lab.design.R.drawable.crown),
                        contentDescription = "Team Lead",
                    )
                }
            }
            Divider(
                color = MaterialTheme.colorScheme.primary,
                thickness = 3.dp
            )
        }
    }
}

@Composable
@Preview
fun TeamDetailsCardPreview() {
    val mockTeam = listOf(
            TeamMember(
                name = "Member 1",
                isLead = true
            ),
            TeamMember(
                name = "Member 2",
            ),
            TeamMember(
                name = "Member 3",
            ),
            TeamMember(
                name = "Member 4",
            ),
            TeamMember(
                name = "Member 5",
            )
    )
    ScavengerHuntTheme {
        AppScreen {
            TeamDetailsCard(teamMember = mockTeam)
        }
    }
}
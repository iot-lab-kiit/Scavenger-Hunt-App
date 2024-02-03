package `in`.iot.lab.teambuilding.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.teambuilding.R

// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    ScavengerHuntTheme {
        TeamHome(rememberNavController())
    }
}


@Composable
fun TeamHome(navController: NavController) {

    Box(
        modifier = Modifier
            .background(Color(0xff11151c))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // Parent Composable
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Scavenger Hunt Text Image
            Image(
                painter = painterResource(id = R.mipmap.scavenger_hunt),
                contentDescription = "Scavenger Hunt Logo",
                modifier = Modifier
                    .height(84.dp)
                    .width(291.dp)
            )

            // Create Team Text Button
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .requiredWidth(width = 196.dp)
                    .requiredHeight(height = 55.dp)
                    .clip(shape = RoundedCornerShape(45.dp))
                    .background(color = Color(0xfff1f2eb))
                    .clickable {
                        navController.navigate("team-building-create-route")
                    }
            ) {

                Text(
                    text = "CREATE TEAM",
                    color = Color(0xff11151c),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .requiredWidth(width = 164.dp)
                )
            }

            // Join Team Text Button
            Box(contentAlignment = Alignment.Center, modifier = Modifier
                .requiredWidth(width = 196.dp)
                .requiredHeight(height = 55.dp)
                .clip(shape = RoundedCornerShape(45.dp))
                .background(color = Color(0xfff1f2eb))
                .clickable {
                    navController.navigate("team-building-join-route")
                }
            ) {

                Text(
                    text = "JOIN TEAM",
                    color = Color(0xff11151c),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .requiredWidth(width = 164.dp)
                )
            }
        }
    }
}
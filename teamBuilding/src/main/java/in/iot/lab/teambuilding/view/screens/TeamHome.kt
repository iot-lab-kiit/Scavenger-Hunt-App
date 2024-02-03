package `in`.iot.lab.teambuilding.view.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import `in`.iot.lab.teambuilding.R
import `in`.iot.lab.teambuilding.view.navigation.TeamScreens


@Composable
fun TeamHome(navController: NavController) {



    Column {
        Button(onClick = { navController.navigate(TeamScreens.CreateScreenRoute.route) }) {
            Text(text = "Create Team")
        }
        Button(onClick = { navController.navigate(TeamScreens.JoinScreenRoute.route) }) {
            Text(text = "Join Team")
        }

    }


}

@Preview
@Composable
fun TeamHomeUi() {
    Surface(modifier = Modifier.fillMaxSize()
            ,color = Color(0xff11151c)
    ) {
        Column(modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Image(painter = painterResource(id = R.mipmap.examplelogo),
                contentDescription = "logo",
                modifier = Modifier
                    .height(84.dp)
                    .width(291.dp)
            )

            Divider(Modifier.size(50.dp)
            , color = MaterialTheme.colorScheme.onBackground)

            Box(contentAlignment = Alignment.Center,modifier = Modifier
                .requiredWidth(width = 196.dp)
                .requiredHeight(height = 55.dp)
                .clip(shape = RoundedCornerShape(45.dp))
                .background(color = Color(0xfff1f2eb))
                .clickable { }) {

                Text(
                    text = "CREATE TEAM",
                    color = Color(0xff11151c),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp),
                    modifier = Modifier
                        .requiredWidth(width = 164.dp))
            }

            Divider(Modifier.size(24.dp)
                , color = MaterialTheme.colorScheme.onBackground)

            Box(contentAlignment = Alignment.Center,modifier = Modifier
                .requiredWidth(width = 196.dp)
                .requiredHeight(height = 55.dp)
                .clip(shape = RoundedCornerShape(45.dp))
                .background(color = Color(0xfff1f2eb))
                .clickable { }) {

                Text(
                    text = "JOIN TEAM",
                    color = Color(0xff11151c),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .requiredWidth(width = 164.dp))
            }
        }
    }
}
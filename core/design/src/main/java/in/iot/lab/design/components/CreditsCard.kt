package `in`.iot.lab.design.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.R

@Preview
@Composable
fun CreditsCard(
    name: String = "Abhranil Dasgupta",
    title: String = "Android Developer"
) {
    OutlinedCard(
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        border = BorderStroke(
            width = 3.dp,
            color = Color(0xFFCC2936)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = name,
                fontSize = 18.sp,
                fontFamily = FontFamily((Font(R.font.montaga_regular)))
            )

            Spacer(modifier = Modifier.padding(vertical = 2.dp))

            Text(
                text = title,
                color = Color(0xFFCC2936),
                fontSize = 12.sp,
                fontFamily = FontFamily((Font(R.font.montserrat_regular)))
            )

            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Image(painter = painterResource(id = R.drawable.github_logo),
                    contentDescription = "Github logo",
                    Modifier
                        .size(32.dp)
                        .clickable { }
                )

                Image(
                    painter = painterResource(id = R.drawable.linkedin_logo),
                    contentDescription = "Github logo",
                    Modifier
                        .size(32.dp)
                        .clickable { }
                )

                Image(
                    painter = painterResource(id = R.drawable.instagram_logo),
                    contentDescription = "Github logo",
                    Modifier
                        .size(32.dp)
                        .clickable { }
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

        }
    }
}
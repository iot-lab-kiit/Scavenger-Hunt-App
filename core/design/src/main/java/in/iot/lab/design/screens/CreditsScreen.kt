package `in`.iot.lab.design.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.CodedByIoT
import `in`.iot.lab.design.components.CreditsCard
import `in`.iot.lab.design.components.TheMatrixHeaderUI

@Preview
@Composable
fun CreditsScreen() {

    val names = listOf(
        "Abhranil Dasgupta",
        "Anirban Basak",
        "Akangkha Sarkar",
        "Bibek Das",
        "Priyanshu Kumar",
        "Vaibhav Raj",
        "Anirudh Sharma",
        "Anshul Kumar",
        "Vaibhav Tiwari"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff11151c))
    ) {

        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.padding(48.dp))

            TheMatrixHeaderUI()

            Spacer(modifier = Modifier.padding(16.dp))

            CodedByIoT(modifier = Modifier.padding(start = 8.dp, end = 8.dp))

            Spacer(modifier = Modifier.padding(24.dp))

            Image(
                painter = painterResource(id = R.drawable.credits_text),
                contentDescription = "Credits",
                contentScale = ContentScale.Inside
            )

            Spacer(modifier = Modifier.padding(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(names) {
                    CreditsCard(name = it)
                }
            }

        }


    }
}
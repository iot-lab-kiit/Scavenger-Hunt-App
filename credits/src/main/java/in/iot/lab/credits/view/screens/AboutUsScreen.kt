package `in`.iot.lab.credits.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.credits.data.models.RemoteAboutUs
import `in`.iot.lab.credits.view.components.CreditsCard
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.AppTopBar
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.design.components.TheMatrixHeaderUI
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.network.state.UiState

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
        AppScreen {

            val list: MutableList<RemoteAboutUs> = mutableListOf()

            for (i in 0..20) {
                list.add(
                    RemoteAboutUs(
                        name = "Anirban Basak",
                        designation = "Android Developer",
                        instagramLink = "Link",
                        githubLink = "Link",
                        linkedIn = "Link"
                    )
                )
            }

            AboutUsSuccessScreen(list)
        }
    }
}


@Composable
fun AboutUsScreen(
    aboutUsData: UiState<List<RemoteAboutUs>>,
    onCancelClick: () -> Unit,
    onRetryClick: () -> Unit
) {


    LaunchedEffect(Unit) {
        onRetryClick()
    }

    AppScreen(
        topBar = { AppTopBar(headerText = "About Us") },
        contentAlignment = Alignment.TopCenter
    ) {

        when (aboutUsData) {
            is UiState.Idle -> {
                // Do Nothing
            }

            is UiState.Loading -> {
                LoadingTransition()
            }

            is UiState.Success -> {
                AboutUsSuccessScreen(aboutUsData.data)
            }

            is UiState.Failed -> {
                ErrorDialog(
                    text = aboutUsData.message,
                    onTryAgain = onRetryClick,
                    onCancel = onCancelClick
                )
            }
        }
    }

}

@Composable
private fun AboutUsSuccessScreen(aboutUsData: List<RemoteAboutUs>) {


    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        TheMatrixHeaderUI(false)

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {


            item(span = { GridItemSpan(2) }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Coded with ❤️ and ☕ by IoT Lab",
                    color = Color(0xFFF1F2EB),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter_regular)),
                    textAlign = TextAlign.Center
                )
            }

            item(span = { GridItemSpan(2) }) {
                Image(
                    modifier = Modifier.padding(vertical = 8.dp),
                    painter = painterResource(id = R.drawable.credits_text),
                    contentDescription = "Credits",
                    contentScale = ContentScale.Inside
                )
            }

            items(aboutUsData) {
                CreditsCard(aboutUs = it)
            }

            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    }
}
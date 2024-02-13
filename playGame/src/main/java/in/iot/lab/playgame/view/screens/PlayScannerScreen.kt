package `in`.iot.lab.playgame.view.screens


import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ErrorDialog
import `in`.iot.lab.design.components.LoadingTransition
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.theme.darkBackGround
import `in`.iot.lab.network.data.models.hint.RemoteHint
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.playgame.view.component.RewardUI
import `in`.iot.lab.playgame.view.event.PlayGameEvent
import `in`.iot.lab.qrcode.scanner.CodeScannerScreen


@Composable
fun PlayScannerScreenControl(
    hintData: UiState<RemoteHint>,
    onCancelClick: () -> Unit,
    setEvent: (PlayGameEvent) -> Unit
) {

    // App Scaffold
    AppScreen {

        // Checking Hint Api Call Status.
        when (hintData) {

            is UiState.Idle -> {
                CodeScannerScreen(
                    onCodeScanned = {
                        setEvent(PlayGameEvent.NetworkIO.UpdatePoints(it))
                    },
                    onFailure = {
                        setEvent(PlayGameEvent.ScannerIO.ScannerFailure)
                    }
                )
            }

            is UiState.Loading -> {
                LoadingTransition()
            }

            is UiState.Success -> {

                // Success Screen
                PlayHintSuccessScreen(
                    hintData = hintData.data,
                    onScanAgainClick = {
                        setEvent(PlayGameEvent.ScannerIO.ResetScanner)
                    },
                    onBackPress = onCancelClick
                )
            }

            is UiState.Failed -> {

                ErrorDialog(
                    text = hintData.message,
                    onCancel = onCancelClick,
                    onTryAgain = {
                        setEvent(PlayGameEvent.ScannerIO.ResetScanner)
                    }
                )
            }
        }
    }
}


@Composable
private fun PlayHintSuccessScreen(
    hintData: RemoteHint,
    onScanAgainClick: () -> Unit,
    onBackPress: () -> Unit
) {

    val uriHandler = LocalUriHandler.current
    var showReward by remember { mutableStateOf(true) }

    BackHandler {
        if (!showReward) onBackPress()
    }

    OutlinedCard(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        border = BorderStroke(
            width = 2.5.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        colors = CardDefaults.outlinedCardColors(containerColor = darkBackGround)
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Hint text
            Text(
                text = "Hint",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.orbitron_regular)),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )


            // Bulb Icon
            if (hintData.image == null)
                Image(
                    painter = painterResource(id = R.drawable.light_bulb),
                    contentDescription = "Hint Light Bulb Image"
                )

            // Image For hint
            hintData.image?.let {
                AsyncImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .size(200.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(hintData.image)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.reward),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }


            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSurface,
                    contentColor = MaterialTheme.colorScheme.surface
                )
            ) {

                // Hint Question Text
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = hintData.question ?: "Hint Question",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.montserratsemibold)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF4B4B4B),
                        textAlign = TextAlign.Center,
                    )
                )
            }

            PrimaryButton(onClick = onScanAgainClick) {
                Text(text = "SCAN AGAIN")
            }
        }
    }


    AnimatedVisibility(visible = showReward) {
        Dialog(
            onDismissRequest = { showReward = false },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            RewardUI(onSkipClick = { showReward = false }) {
                uriHandler.openUri(hintData.answer ?: "")
            }
        }
    }
}
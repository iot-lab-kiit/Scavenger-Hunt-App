package `in`.iot.lab.scavengerhunt.screen

import android.content.res.Configuration
import android.graphics.drawable.Animatable2.AnimationCallback
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.drawable.ScaleDrawable
import coil.request.ImageRequest
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.design.R
import kotlinx.coroutines.delay


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

        // Example usage
        var isSplashing by remember { mutableStateOf(true) }
        if (isSplashing) SplashScreen { isSplashing = false }
        else Text("Next Screen")
    }
}


/**
 * This is the Splash Screen of the App.
 *
 * @param modifier This is optional default parameter which can be passed from the parent function.
 * @param callback This is the function which is executed after the splash screen animation is done.
 */
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    callback: () -> Unit = {}
) {

    // Local Context
    val context = LocalContext.current

    // Image Loader Object
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28)
                add(ImageDecoderDecoder.Factory())
            else add(GifDecoder.Factory())
        }
        .build()


    // Checking Build Version and showing UI accordingly
    if (Build.VERSION.SDK_INT < 28) {
        LaunchedEffect(Unit) {
            delay(6038)
            callback()
        }
    }

    // Parent UI
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        // Gif Loader using Coil
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context)
                    .data(data = R.drawable.splush_splash)
                    .listener(
                        onSuccess = { _, res ->
                            if (Build.VERSION.SDK_INT >= 28) {
                                val drawable =
                                    (res.drawable as ScaleDrawable).child as AnimatedImageDrawable
                                drawable.repeatCount = 0
                                drawable.registerAnimationCallback(
                                    object : AnimationCallback() {
                                        override fun onAnimationEnd(drawable: Drawable?) {
                                            callback()
                                        }
                                    }
                                )
                            }
                        }
                    ).build(),
                imageLoader = imageLoader
            ),
            contentDescription = "Splash Screen",
            modifier = modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )
    }
}
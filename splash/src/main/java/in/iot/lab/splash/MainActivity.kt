package `in`.iot.lab.splash

import android.graphics.drawable.Animatable2.AnimationCallback
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier, callback: () -> Unit = {}) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28)
                add(ImageDecoderDecoder.Factory())
            else add(GifDecoder.Factory())
        }
        .build()

    if (Build.VERSION.SDK_INT < 28) {
        LaunchedEffect(Unit) {
            delay(6038)
            callback()
        }
    }

    Box(
        modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context)
                    .data(data = R.drawable.splush_splash)
                    .listener(
                        onSuccess = { req, res ->
                            if (Build.VERSION.SDK_INT >= 28) {
                                val drawable =
                                    (res.drawable as ScaleDrawable).child as AnimatedImageDrawable
                                drawable.repeatCount = 0
                                drawable.registerAnimationCallback(object : AnimationCallback() {
                                    override fun onAnimationEnd(drawable: Drawable?) {
                                        callback()
                                    }
                                })
                            }
                        }
                    )
                    .build(),
                imageLoader = imageLoader
            ),
            contentDescription = "Splash Screen",
            modifier = modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    // Example usage
    var isSplashing by remember { mutableStateOf(true) }
    if (isSplashing) SplashScreen { isSplashing = false }
    else Text("Next Screen")
}
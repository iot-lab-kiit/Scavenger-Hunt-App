package `in`.iot.lab.design.animation.navigation.entry


import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn


fun appEntryFadeInTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(500))
}

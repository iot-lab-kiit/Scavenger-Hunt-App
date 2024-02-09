package `in`.iot.lab.design.animation.navigation.exit

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut


fun appPopExitFadeAnimation(): ExitTransition {
    return fadeOut(animationSpec = tween(500, easing = LinearEasing))
}
package `in`.iot.lab.dashboard.ui.screen.team.components

import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

data class Comet(var initialAngle: Float, var isDone: Boolean, val color: Color) {
    var distance = (4 * Math.random()).toInt() / 4f + (0.1f * Math.random()).toFloat()
    val offColor = lerp(color, Color.Black, 0.3f)
    val speed = 1f + Math.random().toFloat() * 0.7f
    lateinit var angle: State<Float>
}
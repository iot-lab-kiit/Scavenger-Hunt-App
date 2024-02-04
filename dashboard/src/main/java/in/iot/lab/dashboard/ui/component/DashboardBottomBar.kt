package `in`.iot.lab.dashboard.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import `in`.iot.lab.dashboard.ui.navigation.DashboardRoutes

@Composable
internal fun DashboardBottomBar(
    navController: NavController,
    height: Dp = 100.dp,
    curvatureDepth: Dp = 35.dp,
    modifier: Modifier = Modifier
) {
    val screens = mutableListOf(
        DashboardRoutes.Team,
        DashboardRoutes.Play,
        DashboardRoutes.Leaderboard
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }

    val currentAlign = remember { mutableListOf(0, 1, 2) }

    val alignments = screens.mapIndexed { index, screen ->
        val xw: Float by animateFloatAsState(-1 + 2 * currentAlign.indexOf(index) / (screens.size - 1f))
        val yw: Float by animateFloatAsState(if (currentAlign.indexOf(index) == screens.size / 2) 0f else 0.5f)
        BiasAlignment(xw, yw)
    }.toMutableList()

    if (bottomBarDestination) {
        Box( // NavigationBar uses Surface, which clips children
            modifier = modifier
                .fillMaxWidth()
                .height(height - curvatureDepth)
        ) {
            Box(
                modifier = modifier
                    .zIndex(200f)
                    .fillMaxWidth()
                    .requiredHeight(height + curvatureDepth)
                    .offset(0.dp, -curvatureDepth)
                    .clip(object : Shape {
                        override fun createOutline(
                            size: Size,
                            layoutDirection: LayoutDirection,
                            density: Density
                        ): Outline {
                            val path = Path()
                            val heap = CornerSize(curvatureDepth).toPx(size, density)
                            path.moveTo(0f, size.height)
                            path.lineTo(0f, heap)
//                        // Good for debugging
//                        path.lineTo(size.width / 2, 0f)
//                        path.lineTo(size.width, heap)
                            path.cubicTo(
                                size.width / 3,
                                0f,
                                2 * size.width / 3,
                                0f,
                                size.width,
                                heap
                            )
                            path.lineTo(size.width, size.height)
                            path.close()
                            return Outline.Generic(path)
                        }
                    })
                    .background(Color(0xFFCC2936))
                    .padding(40.dp, 0.dp)
            ) {
                screens.forEachIndexed { index, screen ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.align(alignments[index])
                    ) {
                        Image(
                            painter = painterResource(id = screen.icon),
                            contentDescription = "icon-$index",
                            modifier = modifier
                                .clickable {
                                    currentAlign.remove(index)
                                    currentAlign.add(screens.size / 2, index)
                                    navController.navigate(screen.route)
                                }
                                .padding(10.dp)
                                .size(45.dp)
                        )
                    }
                }
            }
        }
    }
}
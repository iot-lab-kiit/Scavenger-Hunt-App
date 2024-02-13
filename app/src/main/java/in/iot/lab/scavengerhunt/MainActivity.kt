package `in`.iot.lab.scavengerhunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import `in`.iot.lab.design.theme.ScavengerHuntTheme
import `in`.iot.lab.scavengerhunt.navigation.MainNavGraph
import `in`.iot.lab.scavengerhunt.screen.SplashScreen
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ScavengerHuntTheme {

//                runBlocking {
//                    FirebaseAuth.getInstance().signOut()
//                }

                // Variable which says whether to show the splash screen or not
                var showSplash by remember { mutableStateOf(true) }

                // Checking whether to show splash screen or the App
                if (showSplash)
                    SplashScreen { showSplash = false }
                else {
                    // Main Nav Graph of the App
                    MainNavGraph(navHostController = rememberNavController())
                }
            }
        }
    }
}
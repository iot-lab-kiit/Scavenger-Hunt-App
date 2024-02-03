package `in`.iot.lab.teambuilding.view.navigation

import java.lang.IllegalArgumentException

//sealed class TeamScreens {
//    TeamHome,
//    CreateScreen,
//    JoinScreen;
//
//    companion object {
//        fun fromRoute(route : String?) :TeamScreens
//        = when(route?.substringBefore("/")) {
//            TeamHome.name -> TeamHome
//            CreateScreen.name -> CreateScreen
//            JoinScreen.name -> JoinScreen
//            null -> TeamHome
//            else -> throw IllegalArgumentException("Route $route not valid")
//        }
//    }
//}

sealed class TeamScreens(val route: String) {

    object HomeRoute : TeamScreens("home-screen")
    object CreateScreenRoute: TeamScreens("create-screen")
    object  JoinScreenRoute : TeamScreens("join-screen")
}
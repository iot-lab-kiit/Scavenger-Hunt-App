package `in`.iot.lab.network.utils

object NetworkConstants {

    // Base Url
    const val BASE_URL = "https://a35b-103-106-200-60.ngrok-free.app"

    // Auth ENDPOINT
    const val AUTH_ENDPOINT = "/auth"

    // Team Endpoints
    const val CREATE_TEAM_ENDPOINT = "/team"
    const val UPDATE_TEAM_ENDPOINT = "/team/t/{id}"
    const val UPDATE_POINTS_ENDPOINT = "/team/p/{id}"

    // User Endpoints
    const val GET_TEAM_BY_USER_ID = "/user/t/{id}"
    const val GET_USER_BY_ID_ENDPOINT = "/user/u/{id}"

    // Leaderboard Endpoint
    const val GET_LEADERBOARD_ENDPOINT = "/leaderboard"

    // Credits Endpoint
    const val GET_CREDITS_ENDPOINT = "/credits"
}
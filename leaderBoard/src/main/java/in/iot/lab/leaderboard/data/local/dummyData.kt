package `in`.iot.lab.leaderboard.data.local

data class Dummy(
    val id: String,
    val teamName: String,
    val score: Int
)

fun getDummy(): List<Dummy> {
    return listOf(
        Dummy(
            id ="0001", teamName = "Team A", score = 1000
        ),
        Dummy(
            id ="0002", teamName = "Team B", score = 800
        ),
        Dummy(
            id ="0003", teamName = "Team C", score = 900
        ),
        Dummy(
            id ="0004", teamName = "Team D", score = 800
        ),
        Dummy(
            id ="0005", teamName = "Team E", score = 300
        ),
        Dummy(
            id ="0006", teamName = "Team F", score = 800
        ),
        Dummy(
            id ="0007", teamName = "Team G", score = 500
        ),
        Dummy(
            id ="0008", teamName = "Team H", score = 1000
        ),
        Dummy(
            id ="0009", teamName = "Team I", score = 1000
        ),
        Dummy(
            id ="0010", teamName = "Team J", score = 900
        ),
        Dummy(
            id ="0011", teamName = "Team K", score = 700
        ),
        Dummy(
            id ="0012", teamName = "Team L", score = 500
        ),
    )
}
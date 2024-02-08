package `in`.iot.lab.leaderboard.view.event


/**
 * This function is used to define events from the Leader board UI layer to the View Model layer
 *
 * @property GetLeaderBoard This events asks to fetch the Leader board team details.
 */
sealed class LeaderBoardEvent {

    data object GetLeaderBoard : LeaderBoardEvent()
}
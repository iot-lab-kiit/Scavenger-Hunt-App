package `in`.iot.lab.playgame.view.event


/**
 * This sealed class is used to define all the events which would take place in the Play Game
 * Feature.
 *
 * @property ScannerIO This is responsible for all the Scanner Related Events.
 */
sealed class PlayGameEvent {

    sealed class ScannerIO : PlayGameEvent() {

        data object ResetScanner : PlayGameEvent()
        data object ScannerFailure : PlayGameEvent()
    }

    sealed class NetworkIO : PlayGameEvent() {
        data class UpdatePoints(val hintId: String) : NetworkIO()
    }
}
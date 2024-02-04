package `in`.iot.lab.teambuilding.view.events

sealed class TeamBuildingEvent {

    data class CreateTeamApiCall(val teamName: String) : TeamBuildingEvent()

    data object CheckScannerAvailability : TeamBuildingEvent()
}
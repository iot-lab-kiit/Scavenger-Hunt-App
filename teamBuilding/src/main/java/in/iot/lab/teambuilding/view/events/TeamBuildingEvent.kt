package `in`.iot.lab.teambuilding.view.events

sealed class TeamBuildingEvent {

    data object GetUserRegistrationData : TeamBuildingEvent()

    data class SetTeamName(val teamName: String) : TeamBuildingEvent()

    data object CreateTeamApiCall : TeamBuildingEvent()

    data object CheckScannerAvailability : TeamBuildingEvent()
}
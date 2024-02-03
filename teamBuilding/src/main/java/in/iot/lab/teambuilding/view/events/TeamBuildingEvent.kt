package `in`.iot.lab.teambuilding.view.events

sealed class TeamBuildingEvent {
    data object CheckScannerAvailability : TeamBuildingEvent()
}
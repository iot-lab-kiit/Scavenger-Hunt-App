package `in`.iot.lab.teambuilding.view.events

sealed class TeamBuildingEvent {

    sealed class NetworkIO : TeamBuildingEvent() {
        data object GetUserRegistrationData : NetworkIO()

        data object CreateTeamApiCall : NetworkIO()

        data object RegisterTeamApiCall : NetworkIO()

        data object GetTeamData : NetworkIO()
        data class JoinTeam(val teamId: String) : NetworkIO()
    }

    sealed class ScannerIO : TeamBuildingEvent() {
        data object ScannerFailure : ScannerIO()
    }

    sealed class Helper : TeamBuildingEvent() {
        data class SetTeamName(val teamName: String) : Helper()
        data object OnClickInRegisterScreen : Helper()
        data object ResetTeamJoiningState : Helper()
    }
}
package com.geryes.heromanager.model

class FullMission (
    // Mission data
    val id: Long,
    val name: String,
    val description: String,
    val minimumPower: Int,
    val teamId: Long?,
    val state: MissionState,

    // Missing Team data
    val teamName: String?,
    val teamLeaderId : Long?,
    val teamState: TeamState?,
    var totalTeamPower: Int?
) {
    fun getMission(): Mission {
        return Mission(
            id,
            name,
            description,
            minimumPower,
            teamId,
            state
        )
    }

    fun getTeam(): TeamAndPower? {
        return TeamAndPower(
            teamId ?: return null,
            teamName ?: "",
            teamLeaderId,
            teamState ?: TeamState.AVAILABLE,
            totalTeamPower ?: 0
        )
    }
}
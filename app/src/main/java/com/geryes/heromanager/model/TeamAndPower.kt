package com.geryes.heromanager.model

data class TeamAndPower (
    val id: Long,
    val name: String,
    val leaderId: Long?,
    val state: TeamState,
    var totalPower: Int
) {
    fun getTeam(): Team {
        return Team(id, name, leaderId, state)
    }
}

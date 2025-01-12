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

    companion object {
        // When we don't need the power but need to pass a TeamAndPower object
        fun fromTeam(team: Team?): TeamAndPower? {
            if (team == null) return null

            return TeamAndPower(team.id, team.name, team.leaderId, team.state, 0)
        }
    }

}

package com.geryes.heromanager.repository

import androidx.annotation.WorkerThread
import com.geryes.heromanager.database.HeroDao
import com.geryes.heromanager.database.TeamDao
import com.geryes.heromanager.model.FullTeam
import com.geryes.heromanager.model.Team
import com.geryes.heromanager.model.TeamAndPower
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TeamRepository(
    private val dispatcher : CoroutineDispatcher,
    private val teamDao : TeamDao,
    private val heroDao: HeroDao
) {
    fun getAllTeams() : Flow<List<TeamAndPower>> {
        return teamDao.getAllTeams()
    }

    fun getTeamById(id : Long) : Flow<FullTeam?> {
        return teamDao.getTeamById(id)
    }

    @WorkerThread
    suspend fun createTeam(fullTeam : FullTeam) : Long = withContext(dispatcher){
        val tid = teamDao.createTeam(fullTeam.team)
        heroDao.updateHeroesInList(
            fullTeam.members.map { it.copy(teamId = tid) }
        )
        return@withContext tid
    }

    @WorkerThread
    suspend fun updateTeam(oldTeam: FullTeam?, newTeam: FullTeam) : Long = withContext(dispatcher){
        val removedMembers = oldTeam?.members?.filter {
            !newTeam.members.contains(it)
        } ?: emptyList()
        heroDao.removeHeroesFromTeam(removedMembers.map { it.id })

        val tid = teamDao.updateTeam(newTeam.team)

        heroDao.updateHeroesInList(newTeam.members.map {
                hero -> hero.copy(teamId = tid)
        })
        return@withContext tid
    }

    // only updates the team, not the heroes
    @WorkerThread
    suspend fun updateCoreTeam(team: Team) = withContext(dispatcher){
        teamDao.updateTeam(team)
    }

    @WorkerThread
    suspend fun deleteTeam(fullTeam: FullTeam) = withContext(dispatcher){
        heroDao.removeHeroesFromTeam(fullTeam.members.map { it.id })
        teamDao.deleteTeam(fullTeam.team)
    }
}
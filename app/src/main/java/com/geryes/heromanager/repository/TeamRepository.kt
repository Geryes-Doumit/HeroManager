package com.geryes.heromanager.repository

import androidx.annotation.WorkerThread
import com.geryes.heromanager.database.TeamDao
import com.geryes.heromanager.model.FullTeam
import com.geryes.heromanager.model.Team
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TeamRepository(
    private val dispatcher : CoroutineDispatcher,
    private val teamDao : TeamDao
) {
    fun getAllTeams() : Flow<List<Team>> {
        return teamDao.getAllTeams()
    }

    fun getTeamById(id : Long) : Flow<FullTeam?> {
        return teamDao.getTeamById(id)
    }

    @WorkerThread
    suspend fun createTeam(team : Team) : Long = withContext(dispatcher){
        return@withContext teamDao.createTeam(team)
    }

    @WorkerThread
    suspend fun updateTeam(team : Team) : Long = withContext(dispatcher){
        return@withContext teamDao.updateTeam(team)
    }

    @WorkerThread
    suspend fun upsertTeam(team : Team) : Long = withContext(dispatcher){
        return@withContext teamDao.upsertTeam(team)
    }

    @WorkerThread
    suspend fun deleteTeam(team : Team) = withContext(dispatcher){
        teamDao.deleteTeam(team)
    }
}
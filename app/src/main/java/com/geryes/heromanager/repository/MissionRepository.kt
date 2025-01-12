package com.geryes.heromanager.repository

import androidx.annotation.WorkerThread
import com.geryes.heromanager.database.MissionDao
import com.geryes.heromanager.database.TeamDao
import com.geryes.heromanager.model.FullMission
import com.geryes.heromanager.model.Mission
import com.geryes.heromanager.model.MissionState
import com.geryes.heromanager.model.TeamState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MissionRepository(
    private val dispatcher: CoroutineDispatcher,
    private val missionDao: MissionDao,
    private val teamDao: TeamDao
) {
    fun getAllMissions() : Flow<List<Mission>> {
        return missionDao.getAllMissions()
    }

    fun getMissionById(id: Long) : Flow<FullMission?> {
        return missionDao.getMissionById(id)
    }

    @WorkerThread
    suspend fun createMission(mission: Mission) : Long = withContext(dispatcher){
        return@withContext missionDao.createMission(mission)
    }

    @WorkerThread
    suspend fun updateMission(fullMission: FullMission) : Long = withContext(dispatcher) {
        if (fullMission.teamId != null) {
            when (fullMission.state) {
                MissionState.ONGOING -> {
                    val teamAndPower = fullMission.getTeam()
                        ?: return@withContext -1 // can't start a mission without a team
                    if (teamAndPower.state == TeamState.BUSY)
                        return@withContext -1 // can't start a mission with a busy team

                    teamDao.updateTeam(teamAndPower.getTeam().copy(state = TeamState.BUSY))
                }
                else -> {
                    val teamAndPower = fullMission.getTeam()
                    if (teamAndPower != null) {
                        teamDao.updateTeam(teamAndPower.getTeam().copy(state = TeamState.AVAILABLE))
                    }
                }
            }
        }
        return@withContext missionDao.updateMission(fullMission.getMission())
    }

    @WorkerThread
    suspend fun deleteMission(mission: Mission) = withContext(dispatcher){
        return@withContext missionDao.deleteMission(mission)
    }
}
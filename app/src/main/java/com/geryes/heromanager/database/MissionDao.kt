package com.geryes.heromanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.geryes.heromanager.model.FullMission
import com.geryes.heromanager.model.Mission
import kotlinx.coroutines.flow.Flow

@Dao
interface MissionDao {
    @Query("SELECT * FROM missions")
    fun getAllMissions(): Flow<List<Mission>>

    @Transaction
    // A horrible query that works with FullMission
    // @Embedded and @Relation were not enough, or I didn't know how to use them
    @Query("SELECT missions.id, missions.name, missions.description, " +
            "missions.minimumPower, missions.teamId, missions.state, " +
            "teams.name as 'teamName', teams.state as 'teamState', " +
            "teams.leaderId as 'teamLeaderId', " +
            "IFNULL(sum(heroes.power), 0) as 'totalTeamPower' " +
            "FROM missions " +
            "LEFT JOIN teams ON missions.teamId = teams.id " +
            "LEFT JOIN heroes ON teams.id = heroes.teamId " +
            "WHERE missions.id = :id " +
            "GROUP BY missions.id")
    fun getMissionById(id: Long) : Flow<FullMission?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun createMission(mission: Mission) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateMission(mission: Mission) : Long

    @Delete
    fun deleteMission(mission: Mission)
}
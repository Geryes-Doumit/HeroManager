package com.geryes.heromanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.geryes.heromanager.model.FullTeam
import com.geryes.heromanager.model.Team
import com.geryes.heromanager.model.TeamAndPower
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Query("SELECT teams.id, name, leaderId, state, IFNULL(sum(power), 0) as 'totalPower' " +
           "from teams left join heroes on teams.id = heroes.teamId group by teams.id")
    // Without the 'group by' at the end, the query returns null when there are no heroes in the team
    // here, it returns an empty list
    fun getAllTeams() : Flow<List<TeamAndPower>>

    @Transaction
    @Query("SELECT * from teams WHERE id = :id")
    fun getTeamById(id : Long) : Flow<FullTeam?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun createTeam(team: Team) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateTeam(team: Team) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertTeam(team: Team) : Long

    @Delete
    fun deleteTeam(team: Team)
}
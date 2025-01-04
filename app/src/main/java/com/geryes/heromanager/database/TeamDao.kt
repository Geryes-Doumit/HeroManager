package com.geryes.heromanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geryes.heromanager.model.FullTeam
import com.geryes.heromanager.model.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Query("SELECT * from teams")
    fun getAllTeams() : Flow<List<Team>>

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
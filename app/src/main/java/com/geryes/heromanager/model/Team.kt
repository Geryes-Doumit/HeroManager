package com.geryes.heromanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class Team (
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val name : String,
    val leaderId : Long?,
    // val totalPower : Int, // this will be calculated in the Dao
    val state : TeamState,
)

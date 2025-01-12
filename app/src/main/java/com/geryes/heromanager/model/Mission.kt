package com.geryes.heromanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "missions"
)
data class Mission (
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val name : String,
    val description : String,
    val minimumPower: Int,
    val teamId : Long?,
    val state : MissionState
)
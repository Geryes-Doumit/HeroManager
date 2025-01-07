package com.geryes.heromanager.model

import androidx.room.Entity

@Entity(
    tableName = "missions"
)
data class Mission (
    val id : Long,
    val name : String,
    val description : String,
    val minimumPower: Int,
    val teamId : Long?,
)
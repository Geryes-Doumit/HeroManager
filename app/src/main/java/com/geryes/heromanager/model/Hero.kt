package com.geryes.heromanager.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "heroes",
    indices = [Index("teamId")]
)
data class Hero (
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val heroName : String,
    val realName : String,
    val power : Int,
    val teamId : Long?,
)

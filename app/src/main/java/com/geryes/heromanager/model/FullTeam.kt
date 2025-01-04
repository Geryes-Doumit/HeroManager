package com.geryes.heromanager.model

import androidx.room.Embedded
import androidx.room.Relation

class FullTeam (
    @Embedded
    val team : Team,

    @Relation(parentColumn = "leaderId", entityColumn = "id")
    val leader : Hero?,

    @Relation(parentColumn = "id", entityColumn = "teamId")
    val members : List<Hero>,
)
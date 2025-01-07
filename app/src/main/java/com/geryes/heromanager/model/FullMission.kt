package com.geryes.heromanager.model

import androidx.room.Embedded
import androidx.room.Relation

class FullMission (
    @Embedded
    val mission: Mission,

    @Relation(parentColumn = "teamId", entityColumn = "id")
    val team: Team? = null
)
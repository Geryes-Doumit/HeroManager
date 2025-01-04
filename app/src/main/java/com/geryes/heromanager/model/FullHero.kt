package com.geryes.heromanager.model

import androidx.room.Embedded
import androidx.room.Relation

class FullHero (
    @Embedded
    val hero : Hero,

    @Relation(parentColumn = "teamId", entityColumn = "id")
    val team : Team?
)
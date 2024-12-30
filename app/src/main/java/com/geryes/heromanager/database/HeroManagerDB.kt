package com.geryes.heromanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.geryes.heromanager.model.Hero


@Database(
    entities = [
        Hero::class,
        // Team::class,
        // Mission::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HeroManagerDB : RoomDatabase() {
    abstract fun heroDao() : HeroDao
    // abstract fun teamDao() : TeamDao
    // abstract fun missionDao() : MissionDao

    companion object {
        private lateinit var instance : HeroManagerDB

        fun create (context: Context) : HeroManagerDB {
            instance =
                Room.databaseBuilder(context, HeroManagerDB::class.java, "hero_manager.db").build()
            return instance
        }

        fun get() : HeroManagerDB {
            return instance
        }
    }
}
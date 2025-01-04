package com.geryes.heromanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geryes.heromanager.model.FullHero
import com.geryes.heromanager.model.Hero
import kotlinx.coroutines.flow.Flow

@Dao
interface HeroDao {
    @Query("SELECT * from heroes")
    fun getAllHeroes() : Flow<List<Hero>>

    @Query("SELECT * from heroes WHERE id = :id")
    fun getHeroById(id : Long) : Flow<FullHero?>

    @Query("SELECT * from heroes WHERE teamId = :teamId")
    fun getHeroesByTeamId(teamId : Long) : List<Hero>

    @Query("SELECT * from heroes WHERE teamId = :teamId OR teamId IS NULL")
    fun getAllFreeHeroes(teamId: Long?): Flow<List<Hero>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun createHero(hero: Hero) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateHero(hero: Hero) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateHeroesInList(heroes: List<Hero>)

    @Query("UPDATE heroes SET teamId = NULL WHERE id IN (:heroes)")
    fun removeHeroesFromTeam(heroes: List<Long>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertHero(hero: Hero) : Long

    @Delete
    fun deleteHero(hero: Hero)
}
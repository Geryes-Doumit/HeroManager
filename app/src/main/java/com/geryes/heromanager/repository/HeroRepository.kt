package com.geryes.heromanager.repository

import androidx.annotation.WorkerThread
import com.geryes.heromanager.database.HeroDao
import com.geryes.heromanager.model.Hero
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class HeroRepository(
    private val dispatcher : CoroutineDispatcher,
    private val heroDao : HeroDao
) {

    fun getAllHeroes() : Flow<List<Hero>> {
        return heroDao.getAllHeroes()
    }

    fun getHeroById(id : Long) : Flow<Hero?> {
        return heroDao.getHeroById(id)
    }

    @WorkerThread
    suspend fun createHero(hero : Hero) : Long = withContext(dispatcher){
        return@withContext heroDao.createHero(hero)
    }

    @WorkerThread
    suspend fun updateHero(hero : Hero) : Long = withContext(dispatcher){
        return@withContext heroDao.updateHero(hero)
    }

    @WorkerThread
    suspend fun upsertHero(hero : Hero) : Long = withContext(dispatcher){
        return@withContext heroDao.upsertHero(hero)
    }

    @WorkerThread
    suspend fun deleteHero(hero : Hero) = withContext(dispatcher){
        heroDao.deleteHero(hero)
    }
}
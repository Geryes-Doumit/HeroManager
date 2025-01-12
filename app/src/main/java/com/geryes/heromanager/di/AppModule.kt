package com.geryes.heromanager.di

import android.content.Context
import com.geryes.heromanager.database.HeroDao
import com.geryes.heromanager.database.HeroManagerDB
import com.geryes.heromanager.database.MissionDao
import com.geryes.heromanager.database.TeamDao
import com.geryes.heromanager.repository.HeroRepository
import com.geryes.heromanager.repository.MissionRepository
import com.geryes.heromanager.repository.TeamRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob())

    @Singleton
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ) = HeroManagerDB.create(appContext)

    @Singleton
    @Provides
    fun provideHeroDao(db: HeroManagerDB) = db.heroDao()

    @Singleton
    @Provides
    fun provideHeroRepository(
        ioDispatcher: CoroutineDispatcher,
        heroDao: HeroDao
    ) = HeroRepository(ioDispatcher, heroDao)

    @Singleton
    @Provides
    fun provideTeamDao(db: HeroManagerDB) = db.teamDao()

    @Singleton
    @Provides
    fun provideTeamRepository(
        ioDispatcher: CoroutineDispatcher,
        teamDao: TeamDao,
        heroDao: HeroDao
    ) = TeamRepository(ioDispatcher, teamDao, heroDao)

    @Singleton
    @Provides
    fun provideMissionDao(db: HeroManagerDB) = db.missionDao()

    @Singleton
    @Provides
    fun getMissionRepository(
        ioDispatcher: CoroutineDispatcher,
        missionDao: MissionDao,
        teamDao: TeamDao
    ) = MissionRepository(ioDispatcher, missionDao, teamDao)
}
package com.geryes.heromanager.database

import com.geryes.heromanager.model.Hero
import com.geryes.heromanager.model.Team
import com.geryes.heromanager.model.TeamState
import java.util.Random


class FeedDB(
    private val db: HeroManagerDB
) {
    private fun getHeroes(): Array<Hero> {
        val heroes = Array(4) {
            getRandomHero()
            getRandomHero()
            getRandomHero()
            getRandomHero()
        }
        return heroes
    }

    private fun feedTeams(heroes: Array<Hero>): Long {
        val dao: TeamDao = db.teamDao()
        val heroDao: HeroDao = db.heroDao()
        val team = getRandomTeam(heroes)
        val tid = dao.upsertTeam(team)
        heroes.forEach {
            val heroId = heroDao.updateHero(
                it.copy(teamId = tid)
            )
            if (it.heroName in team.name)
                dao.updateTeam(
                    team.copy(
                        id = tid,
                        leaderId = heroId
                    )
                )
        }
        return tid
    }

    fun populate() {
        val heroes = getHeroes()
        feedTeams(heroes)
    }

    fun clear() {
        db.clearAllTables()
    }

    companion object {
        private val rnd: Random = Random()
        private val heroes: Array<Pair<String, String>> = arrayOf(
            Pair("Superman", "Clark Kent"),
            Pair("Batman", "Bruce Wayne"),
            Pair("Spider-Man", "Peter Parker"),
            Pair("Wolverine", "Logan"),
            Pair("Black Widow", "Natasha Romanoff"),
            Pair("SuperGeryes", "Geryes Doumit"),
            Pair("SuperMarc", "Marc Doumit"),
            Pair("SuperEmma", "Emmanuelle Doumit"),
            Pair("SuperDaher", "Daher Doumit"),
            Pair("Thor", "Thor Odinson"),
            Pair("Captain America", "Steve Rogers"),
            Pair("Hulk", "Bruce Banner"),
            Pair("Deadpool", "Wade Wilson"),
        )

        private fun getRandomHero(): Hero {
            val hero = heroes[rnd.nextInt(heroes.size)]
            return Hero(
                id = 0,
                heroName = hero.first,
                realName = hero.second,
                power = rnd.nextInt(1000),
                teamId = null
            )
        }

        private fun getRandomTeam(heroes: Array<Hero>): Team {
            val hero = heroes[rnd.nextInt(heroes.size)]
            val teamName = "Team " + hero.heroName
            return Team(
                id = 0,
                name = teamName,
                leaderId = null,
                state = TeamState.AVAILABLE
            )
        }
    }
}
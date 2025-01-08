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
            Pair("Iron Man", "Tony Stark"),
            Pair("Doctor Strange", "Stephen Strange"),
            Pair("Green Lantern", "Hal Jordan"),
            Pair("The Flash", "Barry Allen"),
            Pair("Aquaman", "Arthur Curry"),
            Pair("Wonder Woman", "Diana Prince"),
            Pair("Green Arrow", "Oliver Queen"),
            Pair("Black Panther", "T'Challa"),
            Pair("Daredevil", "Matt Murdock"),
            Pair("Shazam", "Billy Batson"),
            Pair("Ant-Man", "Scott Lang"),
            Pair("The Wasp", "Hope van Dyne"),
            Pair("Scarlet Witch", "Wanda Maximoff"),
            Pair("Vision", "Vision"),
            Pair("Falcon", "Sam Wilson"),
            Pair("Winter Soldier", "Bucky Barnes"),
            Pair("Hawkeye", "Clint Barton"),
            Pair("Green Goblin", "Norman Osborn"),
            Pair("Loki", "Loki Laufeyson"),
            Pair("Professor X", "Charles Xavier"),
            Pair("Magneto", "Erik Lehnsherr"),
            Pair("Rogue", "Anna Marie"),
            Pair("Gambit", "Remy LeBeau"),
            Pair("Nightcrawler", "Kurt Wagner"),
            Pair("Storm", "Ororo Munroe"),
            Pair("Beast", "Hank McCoy"),
            Pair("Jean Grey", "Phoenix"),
            Pair("Cyclops", "Scott Summers"),
            Pair("Iceman", "Bobby Drake"),
            Pair("The Thing", "Ben Grimm"),
            Pair("Human Torch", "Johnny Storm"),
            Pair("Invisible Woman", "Sue Storm"),
            Pair("Mr. Fantastic", "Reed Richards"),
            Pair("Black Canary", "Dinah Lance"),
            Pair("Zatanna", "Zatanna Zatara"),
            Pair("Catwoman", "Selina Kyle"),
            Pair("Riddler", "Edward Nigma"),
            Pair("Penguin", "Oswald Cobblepot"),
            Pair("Harley Quinn", "Harleen Quinzel"),
            Pair("Poison Ivy", "Pamela Isley"),
            Pair("Bane", "Antonio Diego"),
            Pair("Scarecrow", "Jonathan Crane"),
            Pair("Two-Face", "Harvey Dent"),
            Pair("Robin", "Dick Grayson"),
            Pair("Red Hood", "Jason Todd"),
            Pair("Batgirl", "Barbara Gordon"),
            Pair("Supergirl", "Kara Zor-El"),
            Pair("Martian Manhunter", "J'onn J'onzz"),
            Pair("Hawkman", "Carter Hall"),
            Pair("Hawkgirl", "Shayera Hol"),
            Pair("Doctor Fate", "Kent Nelson"),
            Pair("Blue Beetle", "Jaime Reyes"),
            Pair("Booster Gold", "Michael Carter"),
            Pair("Constantine", "John Constantine"),
            Pair("Silver Surfer", "Norrin Radd"),
            Pair("Galactus", "Galan"),
            Pair("Thanos", "The Mad Titan"),
            Pair("Star-Lord", "Peter Quill"),
            Pair("Gamora", "Gamora Zen-Whoberi Ben Titan"),
            Pair("Drax", "Drax the Destroyer"),
            Pair("Rocket Raccoon", "Rocket"),
            Pair("Groot", "Groot"),
            Pair("Nebula", "Nebula"),
            Pair("Yondu", "Yondu Udonta"),
            Pair("Adam Warlock", "Him"),
            Pair("Doctor Doom", "Victor Von Doom"),
            Pair("The Mandarin", "Xu Wenwu"),
            Pair("Red Skull", "Johann Schmidt"),
            Pair("Kingpin", "Wilson Fisk"),
            Pair("Electro", "Max Dillon"),
            Pair("Sandman", "Flint Marko"),
            Pair("Venom", "Eddie Brock"),
            Pair("Carnage", "Cletus Kasady"),
            Pair("Mystique", "Raven Darkhölme"),
            Pair("Sabretooth", "Victor Creed"),
            Pair("Juggernaut", "Cain Marko"),
            Pair("Apocalypse", "En Sabah Nur"),
            Pair("Archangel", "Warren Worthington III"),
            Pair("Moon Knight", "Marc Spector"),
            Pair("Ghost Rider", "Johnny Blaze"),
            Pair("She-Hulk", "Jennifer Walters"),
            Pair("Iron Fist", "Danny Rand"),
            Pair("Luke Cage", "Carl Lucas"),
            Pair("Jessica Jones", "Jessica Campbell Jones"),
            Pair("Punisher", "Frank Castle"),
            Pair("Nick Fury", "Nicholas Joseph Fury"),
            Pair("Quicksilver", "Pietro Maximoff"),
            Pair("Wasp", "Janet van Dyne"),
            Pair("Captain Marvel", "Carol Danvers"),
            Pair("War Machine", "James Rhodes"),
            Pair("Valkyrie", "Brunnhilde"),
            Pair("Shang-Chi", "Shang-Chi"),
            Pair("Blade", "Eric Brooks"),
            Pair("Morbius", "Michael Morbius"),
            Pair("Elektra", "Elektra Natchios"),
            Pair("Hellboy", "Anung Un Rama"),
            Pair("Spawn", "Al Simmons"),
            Pair("Kick-Ass", "Dave Lizewski"),
            Pair("Hit-Girl", "Mindy Macready"),
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
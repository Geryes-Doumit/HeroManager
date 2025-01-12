package com.geryes.heromanager.database

import com.geryes.heromanager.model.Hero
import com.geryes.heromanager.model.Mission
import com.geryes.heromanager.model.MissionState
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

    private fun feedMissions(tid: Long) {
        val dao: MissionDao = db.missionDao()
        val missions = Array(2) {
            getRandomMission(tid)
            getRandomMission(tid)
        }
        missions.forEach {
            dao.createMission(it)
        }
    }

    fun populate() {
        val heroes = getHeroes()
        val tid = feedTeams(heroes)
        feedMissions(tid)
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
        private val missionNamesAndDesc: Array<Pair<String, String>> = arrayOf(
            Pair("Defeat Venom", "Venom is rampaging in the city! Go and beat him!"),
            Pair("Rescue Hostages", "A group of civilians is being held in a bank by armed robbers. Save them before time runs out!"),
            Pair("Stop a Bank Heist", "Thieves are breaking into the Central Bank. Stop them before they escape with the money!"),
            Pair("Save a Cat", "A scared cat is stuck in a tall tree. Help it get down safely!"),
            Pair("Assist the Firefighters", "A building is on fire downtown! Help the firefighters evacuate people and extinguish the flames."),
            Pair("Catch the Runaway Train", "A runaway train is speeding through the city with no brakes! Get to the front and stop it."),
            Pair("Help a Grandma", "An elderly lady needs help carrying her groceries back home. Lend her a hand!"),
            Pair("Retrieve Stolen Tech", "Advanced technology was stolen from a lab. Track it down and recover it."),
            Pair("Defuse the Bomb", "A villain has planted a bomb in the subway! Find it and defuse it before it detonates."),
            Pair("Find a Lost Dog", "A child is looking for their lost puppy. Search the neighborhood and bring it back."),
            Pair("Stop a Car Chase", "A gang is fleeing the police in high-speed cars. Intercept them and bring them to justice."),
            Pair("Protect a Shipment", "A convoy carrying medical supplies is under threat. Escort it safely to its destination."),
            Pair("Help Rebuild the Park", "A recent battle damaged the city park. Help clean up and restore the area."),
            Pair("Capture the Riddler", "The Riddler has planted clues around the city. Solve them and catch him!"),
            Pair("Escort Civilians", "A group of civilians is trapped in a dangerous area. Lead them to safety."),
            Pair("Stop the Meteor", "A meteor is hurtling toward Earth! Destroy it before it causes catastrophic damage."),
            Pair("Save a Bus Full of Children", "A bus filled with children is teetering on the edge of a bridge. Get everyone out safely."),
            Pair("Deliver Medical Supplies", "A remote village needs urgent medical supplies. Fly them over before it’s too late!"),
            Pair("Stop a Robbery", "A jewelry store is being robbed. Stop the criminals and return the stolen items."),
            Pair("Find Missing Scientists", "Scientists have gone missing in a secret lab. Investigate and rescue them."),
            Pair("Patch a Dam Leak", "A dam is about to burst! Temporarily patch it up until repairs can be made."),
            Pair("Apprehend the Joker", "The Joker is on a rampage, spreading chaos in Gotham. Track him down and stop him."),
            Pair("Save a Falling Plane", "A passenger plane is losing altitude. Stabilize it and ensure a safe landing."),
            Pair("Help Build a Shelter", "A community needs help constructing a temporary shelter after a natural disaster."),
            Pair("Rescue Miners", "A group of miners is trapped underground. Dig through the rubble and bring them out."),
            Pair("Stop a Power Outage", "A villain is sabotaging the city’s power grid. Prevent the blackout and repair the damage."),
            Pair("Save Swimmers", "A group of swimmers is caught in a riptide. Get them to shore safely."),
            Pair("Recover Lost Artifacts", "Priceless artifacts have been stolen from the museum. Retrieve them before they’re sold!"),
            Pair("Stop the Rampaging Robot", "A giant robot is causing havoc downtown. Disable it before it does more damage."),
            Pair("Fix the Water Supply", "The city’s main water line has been damaged. Repair it to restore clean water."),
            Pair("Catch a Pickpocket", "A sneaky pickpocket is stealing wallets in the park. Catch them in the act."),
            Pair("Prevent a Street Fight", "Two rival gangs are about to clash. Intervene and de-escalate the situation."),
            Pair("Save a Whale", "A whale is stranded on the beach. Help guide it back to the ocean."),
            Pair("Catch Escaped Villains", "Several villains have escaped from prison. Track them down and return them to custody."),
            Pair("Deliver a Baby", "A woman is in labor, and there’s no time to reach the hospital. Assist with the delivery."),
            Pair("Calm a Crowd", "A protest has turned into a riot. Step in and restore peace."),
            Pair("Retrieve a Balloon", "A child’s balloon is stuck on top of a tall building. Retrieve it for them."),
            Pair("Clean Up the Beach", "Litter has accumulated on the beach. Join the volunteers and help clean it up."),
            Pair("Stop a Forest Fire", "A forest fire is spreading rapidly. Help contain it before it reaches nearby towns."),
            Pair("Fix a Broken Bridge", "A bridge is damaged and unsafe. Temporarily stabilize it until repairs can be made."),
            Pair("Rescue a Submarine Crew", "A submarine is stranded at the bottom of the ocean. Bring the crew to safety."),
            Pair("Stop the Hydra Invasion", "Hydra agents are infiltrating a secure facility. Repel the attack and secure the area."),
            Pair("Protect the Mayor", "The mayor is under threat during a public event. Ensure their safety."),
            Pair("Find a Cure", "A mysterious illness is spreading. Work with scientists to find and distribute a cure."),
            Pair("Neutralize a Gas Leak", "Toxic gas is leaking from an industrial plant. Seal the leak before it spreads."),
            Pair("Prevent an Avalanche", "An avalanche is about to hit a ski resort. Evacuate the area and prevent disaster."),
            Pair("Reunite a Family", "A child is separated from their parents in a crowded mall. Help them find each other."),
            Pair("Capture Doctor Doom", "Doctor Doom has been spotted planning his next scheme. Intervene and capture him."),

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

        private fun getRandomMission(tid: Long): Mission {
            val missionAndDesc = missionNamesAndDesc[rnd.nextInt(missionNamesAndDesc.size)]

            var teamId: Long? = tid
            if (rnd.nextInt(2) == 1) { // 50% chance of no team assigned
                teamId = null
            }

            return Mission(
                id = 0,
                teamId = teamId,
                name = missionAndDesc.first,
                description = missionAndDesc.second,
                minimumPower = rnd.nextInt(3000),
                state = MissionState.PLANNED
            )
        }
    }
}
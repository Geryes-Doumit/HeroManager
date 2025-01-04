package com.geryes.heromanager.appui.hero

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geryes.heromanager.model.FullHero
import com.geryes.heromanager.model.Hero
import com.geryes.heromanager.model.Team
import com.geryes.heromanager.repository.HeroRepository
import com.geryes.heromanager.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroViewModel @Inject constructor(
    private val repository: HeroRepository,
    private val teamRepository: TeamRepository // only to get the team info
) : ViewModel(){
    var heroName = MutableStateFlow("")
    var heroNameError = MutableStateFlow(true)

    var realName = MutableStateFlow("")
    var realNameError = MutableStateFlow(true)

    var power = MutableStateFlow("0")
    var powerError = MutableStateFlow(false)

    var team = MutableStateFlow<Team?>(null)

    private var initialHero = MutableStateFlow<FullHero?>(null)
    var dataIsDifferent = MutableStateFlow(false)

    private val _id: MutableStateFlow<Long> = MutableStateFlow(-1)

    fun setIdAndGetInfo(id: Long) = viewModelScope.launch {
        _id.value = id
        val heroFlow = repository.getHeroById(id)
        heroFlow.flowOn(Dispatchers.IO).collect { fullHero ->
            initialHero.update { fullHero }
            heroName.update { fullHero?.hero?.heroName ?: "" }
            checkHeroNameError()
            realName.update { fullHero?.hero?.realName ?: "" }
            checkRealNameError()
            power.update { fullHero?.hero?.power.toString() }
            checkPowerError()
            team.update { fullHero?.team }
            checkDataIsDifferent()
        }
    }

    fun checkHeroNameError() {
        heroNameError.value = heroName.value.isEmpty() || heroName.value.length < 2 || heroName.value.length > 50
    }

    fun checkRealNameError() {
        realNameError.value = realName.value.isEmpty() || realName.value.length < 2 || realName.value.length > 50
    }

    fun checkPowerError() {
        if (power.value.toIntOrNull() == null)
            powerError.value = true
        else
            powerError.value = power.value.toInt() < 0
    }

    fun checkDataIsDifferent() {
        dataIsDifferent.value =
            (heroName.value != initialHero.value?.hero?.heroName) ||
                    (realName.value != initialHero.value?.hero?.realName) ||
                    (power.value != initialHero.value?.hero?.power.toString()) ||
                    ((team.value?.id) != initialHero.value?.hero?.teamId)
    }

    fun createHero() = viewModelScope.launch {
        val hero = Hero(
            id = 0,
            heroName = heroName.value,
            realName = realName.value,
            power = power.value.toInt(),
            teamId = team.value?.id
        )
        val heroId = repository.createHero(hero)
        _id.value = heroId

        updateTeams(null, team.value)
    }

    fun updateHero() = viewModelScope.launch {
        val hero = Hero(
            id = _id.value,
            heroName = heroName.value,
            realName = realName.value,
            power = power.value.toInt(),
            teamId = team.value?.id
        )
        repository.updateHero(hero)

        updateTeams(initialHero.value?.team, team.value)
    }

    fun deleteHero() = viewModelScope.launch {
        val hero = Hero(
            id = _id.value,
            heroName = heroName.value,
            realName = realName.value,
            power = power.value.toInt(),
            teamId = team.value?.id
        )
        repository.deleteHero(hero)

        updateTeams(initialHero.value?.team, null)
    }

    private fun updateTeams(oldTeam: Team?, newTeam: Team?) = viewModelScope.launch {
        if (oldTeam == null && newTeam == null) {
            return@launch
        }

        if (oldTeam == null) { // newTeam here is not null (added a team to the hero)
            val heroesInTeam = repository.getHeroesByTeamId(newTeam!!.id)
            val totalPower = heroesInTeam.sumOf { it.power }

            teamRepository.updateTeam(
                newTeam.copy(
                    totalPower = totalPower
                )
            )

            return@launch
        }

        if (newTeam == null) { // oldTeam here is not null (removed a team from the hero)
            val heroesInTeam = repository.getHeroesByTeamId(oldTeam.id)
            val totalPower = heroesInTeam.sumOf { it.power }
            val newLeaderId = if (oldTeam.leaderId == _id.value) null else oldTeam.leaderId

            teamRepository.updateTeam(
                oldTeam.copy(
                    totalPower = totalPower,
                    leaderId = newLeaderId
                )
            )

            return@launch
        }

        if (oldTeam.id == newTeam.id) { // in case we changed the power of the hero
            val heroesInTeam = repository.getHeroesByTeamId(newTeam.id)
            val totalPower = heroesInTeam.sumOf { it.power }

            teamRepository.updateTeam(
                newTeam.copy(
                    totalPower = totalPower
                )
            )
        }
        else { // in case we changed the team of the hero
            val heroesInNewTeam = repository.getHeroesByTeamId(newTeam.id)
            val totalNewTeamPower = heroesInNewTeam.sumOf { it.power }
            val heroesInOldTeam = repository.getHeroesByTeamId(oldTeam.id)
            val totalOldTeamPower = heroesInOldTeam.sumOf { it.power }

            // if the hero was the leader of the old team, we need to remove him from the leader position
            val newLeaderId = if (oldTeam.leaderId == _id.value) null else oldTeam.leaderId

            teamRepository.updateTeam(
                newTeam.copy(
                    totalPower = totalNewTeamPower
                )
            )

            teamRepository.updateTeam(
                oldTeam.copy(
                    totalPower = totalOldTeamPower,
                    leaderId = newLeaderId
                )
            )
        }
    }
}
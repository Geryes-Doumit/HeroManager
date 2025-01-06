package com.geryes.heromanager.appui.team

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geryes.heromanager.model.FullTeam
import com.geryes.heromanager.model.Hero
import com.geryes.heromanager.model.Team
import com.geryes.heromanager.model.TeamState
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
class TeamViewModel @Inject constructor(
    private val repository: TeamRepository,
    private val heroRepository: HeroRepository
) : ViewModel() {
    var teamName = MutableStateFlow("")
    var teamNameError = MutableStateFlow(true)

    var leader = MutableStateFlow<Hero?>(null)

    var members = SnapshotStateList<Hero>()

    private var initialTeam = MutableStateFlow<FullTeam?>(null)

    private val _id: MutableStateFlow<Long> = MutableStateFlow(-1)

    fun setIdAndGetInfo(id: Long) = viewModelScope.launch {
        _id.value = id
        repository.getTeamById(id)
            .flowOn(Dispatchers.IO).collect { team ->
                initialTeam.update { team }
                leader.update { team?.leader }
                members.clear()
                members.addAll(team?.members ?: emptyList())
                teamName.update { team?.team?.name ?: "" }
                checkTeamNameError()
            }
    }

    fun checkTeamNameError() {
        teamNameError.value = teamName.value.isEmpty()
                || teamName.value.length < 2 || teamName.value.length > 50
    }

    fun createTeam() = viewModelScope.launch {
        val team = Team(
            id = 0,
            name = teamName.value,
            leaderId = leader.value?.id,
            state = TeamState.AVAILABLE
        )
        val newId = repository.createTeam(team)
        heroRepository.updateHeroesInList(members.map {
            hero -> hero.copy(teamId = newId)
        })
    }

    fun updateTeam() = viewModelScope.launch {
        val team = Team(
            id = _id.value,
            name = teamName.value,
            leaderId = leader.value?.id,
            state = initialTeam.value?.team?.state ?: TeamState.AVAILABLE
        )
        val removedMembers = initialTeam.value?.members?.filter { !members.contains(it) } ?: emptyList()
        heroRepository.removeHeroesFromTeam(removedMembers.map { it.id })

        heroRepository.updateHeroesInList(members.map {
                hero -> hero.copy(teamId = team.id)
        })

        repository.updateTeam(team)
    }

    fun deleteTeam() = viewModelScope.launch {
        if (initialTeam.value == null) return@launch
        repository.deleteTeam(initialTeam.value!!.team)
        heroRepository.removeHeroesFromTeam(initialTeam.value!!.members.map { it.id })
    }
}

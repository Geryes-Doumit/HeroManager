package com.geryes.heromanager.appui.team

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geryes.heromanager.model.FullTeam
import com.geryes.heromanager.model.Hero
import com.geryes.heromanager.model.Team
import com.geryes.heromanager.model.TeamState
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
    private val repository: TeamRepository
) : ViewModel() {
    var teamName = MutableStateFlow("")
    var teamNameError = MutableStateFlow(true)

    var leader = MutableStateFlow<Hero?>(null)

    var members = SnapshotStateList<Hero>()

    var initialTeam = MutableStateFlow<FullTeam?>(null)

    var dataIsDifferent = MutableStateFlow(false)

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

    fun checkDataIsDifferent() {
        val teamNameIsDifferent = teamName.value != initialTeam.value?.team?.name
        val leaderIsDifferent = leader.value?.id != initialTeam.value?.leader?.id
        val membersAreDifferent = checkMembersAreDifferent()
        dataIsDifferent.value = teamNameIsDifferent || leaderIsDifferent || membersAreDifferent
    }

    private fun checkMembersAreDifferent(): Boolean {
        if (members.size != initialTeam.value?.members?.size) return true
        return members.any { !initialTeam.value?.members?.contains(it)!! }
    }

    fun createTeam() = viewModelScope.launch {
        val fullTeam = FullTeam(
            team = Team(
                id = 0,
                name = teamName.value,
                leaderId = leader.value?.id,
                state = TeamState.AVAILABLE
            ),
            leader = leader.value,
            members = members
        )
        repository.createTeam(fullTeam)
    }

    fun updateTeam() = viewModelScope.launch {
        val newTeam = FullTeam(
            team = Team(
                id = _id.value,
                name = teamName.value,
                leaderId = leader.value?.id,
                state = initialTeam.value?.team?.state ?: TeamState.AVAILABLE
            ),
            leader = leader.value,
            members = members
        )
        repository.updateTeam(initialTeam.value, newTeam)
    }

    fun deleteTeam() = viewModelScope.launch {
        if (initialTeam.value == null) return@launch
        repository.deleteTeam(initialTeam.value!!)
    }
}

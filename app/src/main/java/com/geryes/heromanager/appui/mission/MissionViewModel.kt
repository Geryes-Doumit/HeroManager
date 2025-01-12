package com.geryes.heromanager.appui.mission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geryes.heromanager.model.FullMission
import com.geryes.heromanager.model.Mission
import com.geryes.heromanager.model.MissionState
import com.geryes.heromanager.model.TeamAndPower
import com.geryes.heromanager.model.TeamState
import com.geryes.heromanager.repository.MissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(
    private val repository: MissionRepository
) : ViewModel() {
    var missionName = MutableStateFlow("")
    var missionNameError = MutableStateFlow(true)

    var description = MutableStateFlow("")
    var descriptionError = MutableStateFlow(false)

    var minimumPower = MutableStateFlow("0")
    var minimumPowerError = MutableStateFlow(false)

    var missionState = MutableStateFlow(MissionState.PLANNED)

    var team = MutableStateFlow<TeamAndPower?>(null)

    private var initialMission = MutableStateFlow<FullMission?>(null)
    var dataIsDifferent = MutableStateFlow(false)

    private val _id: MutableStateFlow<Long> = MutableStateFlow(-1)
    fun getId() = _id

    fun setIdAndGetInfo(id: Long) = viewModelScope.launch {
        _id.value = id
        repository.getMissionById(id).flowOn(Dispatchers.IO)
            .collect { fullMission ->
                initialMission.update { fullMission }
                missionName.update { fullMission?.name ?: "" }
                checkMissionNameError()
                description.update { fullMission?.description ?: "" }
                checkDescriptionError()
                minimumPower.update { fullMission?.minimumPower.toString() }
                checkMinimumPowerError()
                missionState.update { fullMission?.state ?: MissionState.PLANNED }
                if (fullMission?.teamId != null) {
                    team.update { TeamAndPower(
                        id = fullMission.teamId,
                        name = fullMission.teamName ?: "",
                        leaderId = fullMission.teamLeaderId,
                        state = fullMission.teamState ?: TeamState.AVAILABLE,
                        totalPower = fullMission.totalTeamPower ?: 0
                        )
                    }
                } else team.update { null }

                checkDataIsDifferent()
            }
    }

    fun checkMissionNameError() {
        missionNameError.value = missionName.value.isEmpty()
                || missionName.value.length < 2 || missionName.value.length > 50
    }

    fun checkDescriptionError() {
        descriptionError.value = description.value.length > 300 // desc can be empty
    }

    fun checkMinimumPowerError() {
        if (minimumPower.value.toIntOrNull() == null)
            minimumPowerError.value = true
        else
            minimumPowerError.value = minimumPower.value.toInt() < 1
    }

    fun checkDataIsDifferent() {
        val nameIsDifferent = missionName.value != initialMission.value?.name
        val descriptionIsDifferent = description.value != initialMission.value?.description
        val minimumPowerIsDifferent = minimumPower.value != initialMission.value?.minimumPower.toString()
        val teamIdIsDifferent = team.value?.id != initialMission.value?.teamId
        dataIsDifferent.value = nameIsDifferent || descriptionIsDifferent
                             || minimumPowerIsDifferent || teamIdIsDifferent
    }

    fun createMission() = viewModelScope.launch {
        repository.createMission(
            Mission(
                id = 0,
                name = missionName.value,
                description = description.value,
                minimumPower = minimumPower.value.toInt(),
                teamId = team.value?.id,
                state = MissionState.PLANNED
            )
        )
    }

    fun updateMission() = viewModelScope.launch {
        repository.updateMission(
            FullMission(
                id = initialMission.value!!.id,
                name = missionName.value,
                description = description.value,
                minimumPower = minimumPower.value.toInt(),
                teamId = team.value?.id,
                state = missionState.value,
                teamName = team.value?.name,
                teamLeaderId = team.value?.leaderId,
                teamState = team.value?.state,
                totalTeamPower = team.value?.totalPower
            )
        )
    }

    fun startMission() = viewModelScope.launch{
        missionState.value = MissionState.ONGOING
        updateMission()
    }

    fun endMission() = viewModelScope.launch {
        missionState.value = MissionState.COMPLETED
        updateMission()
    }

    fun resetMissionState() = viewModelScope.launch {
        missionState.value = MissionState.PLANNED
        updateMission()
    }

    fun deleteMission() = viewModelScope.launch {
        if (missionState.value == MissionState.ONGOING)
            return@launch // a toast should be shown

        if (initialMission.value != null)
            repository.deleteMission(initialMission.value!!.getMission())
    }
}

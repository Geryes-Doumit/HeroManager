package com.geryes.heromanager.appui.mission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geryes.heromanager.model.Mission
import com.geryes.heromanager.repository.MissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.geryes.heromanager.utilities.vmutils.Result
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MissionsScreenViewModel @Inject constructor(
    repository: MissionRepository
) : ViewModel() {

    private val _missions = repository.getAllMissions()

    data class UIState(
        val missions: List<Mission>
    )

    val uiState : StateFlow<Result<UIState>> = _missions
        .map { list : List<Mission> ->
            Result.Success(UIState(list))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )
}
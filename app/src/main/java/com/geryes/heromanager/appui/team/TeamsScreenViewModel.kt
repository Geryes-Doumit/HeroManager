package com.geryes.heromanager.appui.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geryes.heromanager.model.TeamAndPower
import com.geryes.heromanager.repository.TeamRepository
import com.geryes.heromanager.utilities.vmutils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TeamsScreenViewModel @Inject constructor(
    private val repository: TeamRepository
) : ViewModel() {
    private val _teams = repository.getAllTeams()

    data class UIState(
        val teams: List<TeamAndPower>
    )

    val uiState: StateFlow<Result<UIState>> = _teams
        .map { list ->
            Result.Success(UIState(list))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )
}
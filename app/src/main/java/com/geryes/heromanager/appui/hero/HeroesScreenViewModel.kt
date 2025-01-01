package com.geryes.heromanager.appui.hero

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geryes.heromanager.model.Hero
import com.geryes.heromanager.repository.HeroRepository
import com.geryes.heromanager.utilities.vmutils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class HeroesScreenViewModel @Inject constructor (
    private val repository: HeroRepository
) : ViewModel() {

    private val _heroes = repository.getAllHeroes()

    data class UIState (
        val heroes : List<Hero>
    )

    val uiState : StateFlow<Result<UIState>> = _heroes
        .map { list : List<Hero> ->
            Result.Success(UIState(list))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    sealed class UIEvent {
        data class OnDelete(val hero: Hero) : UIEvent()
    }
}
package com.geryes.heromanager.appui.hero

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geryes.heromanager.model.Hero
import com.geryes.heromanager.repository.HeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateHeroScreenViewModel @Inject constructor(
    private val repository: HeroRepository
) : ViewModel(){
    private val _id: MutableStateFlow<Long> = MutableStateFlow(-1)

    fun checkHeroNameError(heroName: String): Boolean {
        return heroName.isEmpty() || heroName.length < 2 || heroName.length > 50
    }

    fun checkRealNameError(realName: String): Boolean {
        return realName.isEmpty() || realName.length < 2 || realName.length > 50
    }

    fun checkPowerError(power: String): Boolean {
        if (power.toIntOrNull() == null) return true

        return power.toInt() < 0
    }

    fun createHero(hero: Hero) = viewModelScope.launch {
        val heroId = repository.createHero(hero)
        _id.value = heroId
    }
}
package com.geryes.heromanager.appui.hero

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geryes.heromanager.model.Hero
import com.geryes.heromanager.repository.HeroRepository
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

) : ViewModel(){
    var heroName = MutableStateFlow("")
    var heroNameError = MutableStateFlow(true)

    var realName = MutableStateFlow("")
    var realNameError = MutableStateFlow(true)

    var power = MutableStateFlow("0")
    var powerError = MutableStateFlow(false)

    private var initialHero = MutableStateFlow<Hero?>(null)
    var dataIsDifferent = MutableStateFlow(false)

    private val _id: MutableStateFlow<Long> = MutableStateFlow(-1)

    fun setIdAndGetInfo(id: Long) = viewModelScope.launch {
        _id.value = id
        val heroFlow = repository.getHeroById(id)
        heroFlow.flowOn(Dispatchers.IO).collect { hero ->
            initialHero.update { hero }
            heroName.update { hero?.heroName ?: "" }
            checkHeroNameError()
            realName.update { hero?.realName ?: "" }
            checkRealNameError()
            power.update { hero?.power.toString() }
            checkPowerError()
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
                    (heroName.value != initialHero.value?.heroName) ||
                    (realName.value != initialHero.value?.realName) ||
                    (power.value != initialHero.value?.power.toString())
    }

    fun createHero() = viewModelScope.launch {
        val hero = Hero(
            id = 0,
            heroName = heroName.value,
            realName = realName.value,
            power = power.value.toInt(),
            teamId = null
        )
        val heroId = repository.createHero(hero)
        _id.value = heroId
    }

    fun updateHero() = viewModelScope.launch {
        val hero = Hero(
            id = _id.value,
            heroName = heroName.value,
            realName = realName.value,
            power = power.value.toInt(),
            teamId = null
        )
        repository.updateHero(hero)
    }

    fun deleteHero() = viewModelScope.launch {
        val hero = Hero(
            id = _id.value,
            heroName = heroName.value,
            realName = realName.value,
            power = power.value.toInt(),
            teamId = null
        )
        repository.deleteHero(hero)
    }
}
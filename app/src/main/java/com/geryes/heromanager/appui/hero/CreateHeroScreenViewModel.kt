package com.geryes.heromanager.appui.hero

import androidx.lifecycle.ViewModel
import com.geryes.heromanager.repository.HeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateHeroScreenViewModel @Inject constructor(
    private val repository: HeroRepository
) : ViewModel(){
    //TODO
}
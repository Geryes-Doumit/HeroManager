package com.geryes.heromanager.appui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geryes.heromanager.database.FeedDB
import com.geryes.heromanager.database.HeroManagerDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val db : HeroManagerDB,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    fun onClear () = viewModelScope.launch {
        withContext(dispatcher) {
            FeedDB(db).clear()
        }
    }

    fun onFill () = viewModelScope.launch {
        withContext(dispatcher) {
            FeedDB(db).populate()
        }
    }
}
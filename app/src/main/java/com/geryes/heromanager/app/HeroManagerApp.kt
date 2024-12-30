package com.geryes.heromanager.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HeroManagerApp : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
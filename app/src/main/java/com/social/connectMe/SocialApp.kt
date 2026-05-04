package com.social.connectMe

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SocialApp : Application() {

    override fun onCreate() {
        super.onCreate()
        println("Hello")
    }

    companion object {
        // State is now managed by Compose
        var count by mutableIntStateOf(0)
            private set



        fun incrementCount() {
            count++
        }
    }
}

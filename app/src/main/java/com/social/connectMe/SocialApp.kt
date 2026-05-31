package com.social.connectMe

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SocialApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}

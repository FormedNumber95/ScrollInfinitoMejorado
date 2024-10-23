package com.example.scrollinfinito

import android.app.Application

class TaskApllication:Application() {
    companion object{
        lateinit var prefs:Preferences
    }
    override fun onCreate() {
        super.onCreate()
        prefs=Preferences(baseContext)
    }
}
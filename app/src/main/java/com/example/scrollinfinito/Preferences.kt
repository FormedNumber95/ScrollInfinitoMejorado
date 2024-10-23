package com.example.scrollinfinito

import android.content.Context
import android.content.SharedPreferences

class Preferences (context: Context){
    companion object{
        const val PREFS_NAME ="myDatabase"
    }
    val prefs:SharedPreferences=context.getSharedPreferences(PREFS_NAME,0)
}
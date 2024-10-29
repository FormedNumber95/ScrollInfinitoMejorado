package com.example.scrollinfinito

import android.app.Application

/**
 * Clase de aplicación que inicializa las preferencias compartidas al iniciar la aplicación.
 * Extiende de [Application] para establecer un contexto de aplicación global.
 */

class TaskApllication:Application() {
    companion object{
        /** Instancia de la clase Preferences para almacenar y recuperar datos de preferencias compartidas */
        lateinit var prefs:Preferences
    }
    /**
     * Método onCreate que se llama al iniciar la aplicación.
     * Inicializa la instancia de [Preferences] con el contexto de la aplicación.
     */
    override fun onCreate() {
        super.onCreate()
        prefs=Preferences(baseContext)
    }
}
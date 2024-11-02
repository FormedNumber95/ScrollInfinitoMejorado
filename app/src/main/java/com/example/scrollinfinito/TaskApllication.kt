package com.example.scrollinfinito

import android.app.Application

/**
 * Clase de aplicación personalizada que inicializa la base de datos al inicio de la aplicación.
 */
class TaskApllication : Application() {
    // Instancia de DatabaseHelper para gestionar las operaciones de la base de datos.
    lateinit var dbHelper: DatabaseHelper

    /**
     * Método llamado cuando la aplicación se crea.
     * Se inicializa el DatabaseHelper para permitir operaciones de base de datos en toda la aplicación.
     */
    override fun onCreate() {
        super.onCreate()
        dbHelper = DatabaseHelper(this) // Inicializa el helper de la base de datos
    }
}

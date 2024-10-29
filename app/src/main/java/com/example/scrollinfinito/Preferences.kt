package com.example.scrollinfinito

import android.content.Context
import android.content.SharedPreferences

/**
 * Clase que maneja las preferencias compartidas para almacenar y recuperar datos relacionados con las tareas.
 * @param context Contexto de la aplicación utilizado para acceder a SharedPreferences.
 */
class Preferences(context: Context) {

    companion object {
        /** Nombre del archivo de preferencias compartidas */
        const val PREFS_NAME = "myDatabase"

        /** Clave para almacenar el conjunto de tareas */
        const val TASKS = "tasks_value"
    }

    /** Instancia de SharedPreferences utilizada para almacenar las tareas */
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    /**
     * Guarda la lista de tareas en SharedPreferences.
     * @param tasks Lista de tareas a guardar.
     */
    fun saveTasks(tasks: List<String>) {
        prefs.edit().putStringSet(TASKS, tasks.toSet()).apply()
    }

    /**
     * Recupera la lista de tareas desde SharedPreferences.
     * @return Lista mutable de tareas guardadas; devuelve una lista vacía si no hay tareas almacenadas.
     */
    fun getTasks(): MutableList<String> {
        return prefs.getStringSet(TASKS, emptySet<String>())?.toMutableList() ?: mutableListOf()
    }
}

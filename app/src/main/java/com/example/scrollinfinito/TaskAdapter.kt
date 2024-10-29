package com.example.scrollinfinito

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador para el RecyclerView que muestra una lista de tareas.
 * @param tasks Lista de tareas a mostrar en el RecyclerView.
 * @param onItemDone Función de callback que se ejecuta cuando se marca una tarea como completada.
 */
class TaskAdapter(
    private val tasks: List<String>,
    private val onItemDone: (Int) -> Unit
) : RecyclerView.Adapter<TaskViewHolder>() {

    /**
     * Crea un nuevo ViewHolder para representar un elemento de la lista.
     * @param parent El ViewGroup en el que se añadirá el nuevo ViewHolder.
     * @param viewType Tipo de vista del nuevo ViewHolder.
     * @return Un nuevo TaskViewHolder inicializado con la vista de un elemento de tarea.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.item_task, parent, false))
    }

    /**
     * Devuelve el número de elementos en la lista de tareas.
     * @return Número de tareas en la lista.
     */
    override fun getItemCount() = tasks.size

    /**
     * Vincula los datos de una tarea específica a un ViewHolder.
     * @param holder TaskViewHolder que se actualizará con los datos de la tarea.
     * @param position Posición del elemento en la lista de tareas.
     */
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.render(tasks[position], onItemDone)
    }
}

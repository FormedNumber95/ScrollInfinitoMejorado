package com.example.scrollinfinito

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador para gestionar la lista de tareas en un RecyclerView.
 *
 * @param tasks La lista mutable de tareas a mostrar.
 * @param onItemDone Callback que se ejecuta cuando se marca una tarea como completada.
 */
class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onItemDone: (Int) -> Unit
) : RecyclerView.Adapter<TaskViewHolder>() {

    /**
     * Crea un nuevo ViewHolder para representar un elemento de la lista de tareas.
     *
     * @param parent El ViewGroup al que se adjuntará el nuevo ViewHolder.
     * @param viewType El tipo de vista de los elementos que se crean.
     * @return Un nuevo TaskViewHolder que contiene la vista para un elemento.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    /**
     * Asocia los datos de una tarea a un ViewHolder específico.
     *
     * @param holder El ViewHolder que contiene la vista para un elemento de la lista.
     * @param position La posición de la tarea en la lista.
     */
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.render(tasks[position].description) { onItemDone(position) }
    }

    /**
     * Devuelve el número total de tareas en la lista.
     *
     * @return El tamaño de la lista de tareas.
     */
    override fun getItemCount(): Int = tasks.size
}

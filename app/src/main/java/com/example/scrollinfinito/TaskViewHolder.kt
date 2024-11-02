package com.example.scrollinfinito

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * ViewHolder para representar un elemento de tarea en el RecyclerView.
 *
 * @param view La vista que contiene los elementos visuales para una tarea.
 */
class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvTask: TextView = view.findViewById(R.id.tvTask) // Texto de la tarea
    private val imgCheck: ImageView = view.findViewById(R.id.imgCheck) // Imagen de verificación

    /**
     * Asocia la tarea y establece el comportamiento al marcar la tarea como completada.
     *
     * @param task La descripción de la tarea que se mostrará.
     * @param onItemDone Callback que se ejecuta cuando se hace clic en la imagen de verificación.
     */
    fun render(task: String, onItemDone: (Int) -> Unit) {
        tvTask.text = task // Establece el texto de la tarea
        imgCheck.setOnClickListener { onItemDone(adapterPosition) } // Configura el clic en la imagen
    }
}

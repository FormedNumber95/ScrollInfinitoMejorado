package com.example.scrollinfinito

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * ViewHolder para el RecyclerView que muestra una tarea individual.
 * @param view Vista de cada elemento de tarea.
 */

class TaskViewHolder (view:View):RecyclerView.ViewHolder(view){
    /** TextView que muestra el texto de la tarea */
    private val tvTask:TextView=view.findViewById(R.id.tvTask)
    /** ImageView que representa el ícono para marcar la tarea como completada */
    private val imgCheck:ImageView=view.findViewById(R.id.imgCheck)

    /**
     * Vincula la tarea a los elementos de la vista y asigna un listener para el icono de completado.
     * @param task Tarea a mostrar en el TextView.
     * @param onItemDone Función de callback que se llama al hacer clic en el icono de completado.
     */
    fun render(taks:String,onItemDone:(Int) -> Unit){
        tvTask.text=taks
        imgCheck.setOnClickListener{onItemDone(adapterPosition)}
    }
}
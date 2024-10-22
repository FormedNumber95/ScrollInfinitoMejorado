package com.example.scrollinfinito

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskViewHolder (view:View):RecyclerView.ViewHolder(view){
    private val tvTask:TextView=view.findViewById(R.id.tvTask)

    fun render(taks:String){
        tvTask.text=taks
    }
}
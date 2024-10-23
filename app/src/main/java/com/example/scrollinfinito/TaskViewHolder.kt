package com.example.scrollinfinito

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskViewHolder (view:View):RecyclerView.ViewHolder(view){
    private val tvTask:TextView=view.findViewById(R.id.tvTask)
    private val imgCheck:ImageView=view.findViewById(R.id.imgCheck)

    fun render(taks:String,onItemDone:(Int) -> Unit){
        tvTask.text=taks
        imgCheck.setOnClickListener{onItemDone(adapterPosition)}
    }
}
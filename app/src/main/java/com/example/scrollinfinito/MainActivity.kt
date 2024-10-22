package com.example.scrollinfinito

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var btnAniadir:Button
    lateinit var txtTarea:EditText
    lateinit var rvTarea:RecyclerView
    var task= mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private fun aniadirTarea(){
        initView()
        initListener()
    }

    private fun initListener() {
        btnAniadir.setOnClickListener{addTask()}
    }

    private fun addTask() {
        val taskToAdd=txtTarea.text.toString()
        task.add(taskToAdd)
    }

    private fun initView() {
        btnAniadir=findViewById(R.id.btnAniadir)
        txtTarea=findViewById(R.id.txtTarea)
        rvTarea=findViewById(R.id.rvTarea)
    }
}
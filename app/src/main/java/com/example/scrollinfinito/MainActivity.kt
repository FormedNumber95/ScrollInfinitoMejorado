package com.example.scrollinfinito

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var btnAniadir:Button
    lateinit var txtTarea:EditText
    lateinit var rvTarea:RecyclerView

    lateinit var adapter:TaskAdapter

    var tasks= mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        aniadirTarea()
    }

    private fun aniadirTarea(){
        initView()
        initListener()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rvTarea.layoutManager=LinearLayoutManager(this)
        adapter=TaskAdapter(tasks)
        rvTarea.adapter=adapter
    }

    private fun initListener() {
        btnAniadir.setOnClickListener{addTask()}
    }

    private fun addTask() {
        val taskToAdd=txtTarea.text.toString()
        tasks.add(taskToAdd)
    }

    private fun initView() {
        btnAniadir=findViewById(R.id.btnAniadir)
        txtTarea=findViewById(R.id.txtTarea)
        rvTarea=findViewById(R.id.rvTarea)
    }
}
package com.example.scrollinfinito

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Main, que ejecuta toda la aplicacion
 */
class MainActivity : AppCompatActivity() {

    /** Botón para añadir una nueva tarea */
    lateinit var btnAniadir: Button

    /** Campo de texto para introducir la tarea */
    lateinit var txtTarea: EditText

    /** Vista de reciclador que muestra la lista de tareas */
    lateinit var rvTarea: RecyclerView

    /** Adaptador para la vista de reciclador de tareas */
    lateinit var adapter: TaskAdapter

    /**Sonido que se ejecuta al añadir una nueva tarea*/
    lateinit var mp: MediaPlayer

    /** Lista mutable de tareas */
    var tasks = mutableListOf<String>()

    /**
     * Método onCreate que inicializa la actividad y establece el contenido de la vista.
     * @param savedInstanceState Estado previamente guardado de la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        aniadirTarea()
    }

    /**
     * Método que inicializa las vistas, escuchadores y el RecyclerView.
     */
    private fun aniadirTarea() {
        initView()
        initListener()
        initRecyclerView()
    }

    /**
     * Inicializa el RecyclerView, asignando un LayoutManager y el adaptador.
     * La lista de tareas se obtiene de las preferencias guardadas.
     */
    private fun initRecyclerView() {
        tasks = TaskApllication.prefs.getTasks()
        rvTarea.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(tasks) { deleteTask(it) }
        rvTarea.adapter = adapter
    }

    /**
     * Elimina una tarea de la lista en la posición especificada.
     * @param posicion La posición de la tarea a eliminar en la lista
     */
    private fun deleteTask(posicion: Int) {
        tasks.removeAt(posicion)
        adapter.notifyDataSetChanged()
        TaskApllication.prefs.saveTasks(tasks)
    }

    /**
     * Asigna un listener de clic al botón de añadir tarea.
     */
    private fun initListener() {
        btnAniadir.setOnClickListener { addTask() }
    }

    /**
     * Añade una nueva tarea a la lista si el campo de texto no está vacío.
     * La lista de tareas actualizada se guarda en las preferencias.
     */
    private fun addTask() {
        if (!txtTarea.text.toString().trim().isEmpty()) {
            val taskToAdd = txtTarea.text.toString()
            tasks.add(taskToAdd)
            mp.start()
            adapter.notifyDataSetChanged()
            txtTarea.setText("")
            TaskApllication.prefs.saveTasks(tasks)
        }
    }

    /**
     * Inicializa las vistas de la actividad obteniéndolas del diseño.
     */
    private fun initView() {
        mp = MediaPlayer.create(this, R.raw.ding)
        btnAniadir = findViewById(R.id.btnAniadir)
        txtTarea = findViewById(R.id.txtTarea)
        rvTarea = findViewById(R.id.rvTarea)
    }
}

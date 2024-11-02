package com.example.scrollinfinito

import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Actividad principal que gestiona la interfaz de usuario para añadir y eliminar tareas.
 */
class MainActivity : AppCompatActivity() {

    lateinit var btnAniadir: Button       // Botón para añadir tareas
    lateinit var txtTarea: EditText       // Campo de texto para la descripción de la tarea
    lateinit var rvTarea: RecyclerView    // Vista de lista para mostrar las tareas
    lateinit var adapter: TaskAdapter      // Adaptador para manejar la lista de tareas
    lateinit var mp: MediaPlayer           // Reproductor de audio para efectos de sonido
    var tasks = mutableListOf<Task>()      // Lista mutable de tareas

    /**
     * Método llamado al crear la actividad.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        aniadirTarea()             // Inicializa la tarea
        attachSwipeToDelete()      // Añade funcionalidad de deslizar para eliminar
    }

    /**
     * Inicializa la tarea de añadir y configurar la vista.
     */
    private fun aniadirTarea() {
        initView()                // Inicializa las vistas
        initListener()            // Configura los escuchadores de eventos
        initRecyclerView()        // Inicializa la vista de la lista
    }

    /**
     * Inicializa la vista de la lista de tareas y carga las tareas de la base de datos.
     */
    private fun initRecyclerView() {
        tasks = (application as TaskApllication).dbHelper.getTasks() // Carga de la base de datos
        rvTarea.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(tasks) { deleteTask(it) }
        rvTarea.adapter = adapter
    }

    /**
     * Elimina una tarea de la lista y de la base de datos.
     *
     * @param position La posición de la tarea en la lista.
     */
    private fun deleteTask(position: Int) {
        val task = tasks[position]

        Log.d("MainActivity", "Deleting task with ID: ${task.id}")

        tasks.removeAt(position)  // Elimina la tarea de la lista
        adapter.notifyDataSetChanged() // Notifica al adaptador sobre los cambios

        (application as TaskApllication).dbHelper.deleteTask(task.id) // Elimina de la base de datos
    }

    /**
     * Configura el escuchador para el botón de añadir tarea.
     */
    private fun initListener() {
        btnAniadir.setOnClickListener { addTask() }
    }

    /**
     * Añade una nueva tarea a la base de datos y actualiza la lista.
     */
    private fun addTask() {
        val taskDescription = txtTarea.text.toString().trim()
        if (taskDescription.isNotEmpty()) {
            val dbHelper = (application as TaskApllication).dbHelper
            val taskId = dbHelper.addTask(taskDescription) // Inserta en la base de datos
            val newTask = Task(id = taskId, description = taskDescription)

            tasks.add(newTask) // Añade la nueva tarea a la lista
            mp.start()         // Reproduce el sonido
            adapter.notifyDataSetChanged() // Notifica al adaptador sobre los cambios
            txtTarea.setText("") // Limpia el campo de texto
        }
    }

    /**
     * Inicializa las vistas y el reproductor de audio.
     */
    private fun initView() {
        mp = MediaPlayer.create(this, R.raw.ding)
        btnAniadir = findViewById(R.id.btnAniadir)
        txtTarea = findViewById(R.id.txtTarea)
        rvTarea = findViewById(R.id.rvTarea)
    }

    /**
     * Adjunta la funcionalidad de deslizar para eliminar tareas en la lista.
     */
    private fun attachSwipeToDelete() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            /**
             * Dibuja la vista del elemento mientras se desliza.
             *
             * @param c El objeto Canvas para dibujar.
             * @param recyclerView La vista de la lista.
             * @param viewHolder El ViewHolder del elemento que se está deslizando.
             * @param dX Desplazamiento en el eje X.
             * @param dY Desplazamiento en el eje Y.
             * @param actionState El estado de la acción.
             * @param isCurrentlyActive Indica si la acción está activa.
             */
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    itemView.setBackgroundColor(Color.RED)
                    itemView.translationX = dX
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            /**
             * Maneja la acción de deslizar y elimina la tarea.
             *
             * @param viewHolder El ViewHolder del elemento que fue deslizado.
             * @param direction La dirección en la que se deslizó el elemento.
             */
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                deleteTask(position)
            }

            /**
             * Restaura el color de fondo del elemento después de deslizar.
             *
             * @param recyclerView La vista de la lista.
             * @param viewHolder El ViewHolder del elemento.
             */
            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT)
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rvTarea)
    }
}

package com.example.scrollinfinito

import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
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
        attachSwipeToDelete()
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

    /**
     * Configura el gesto de deslizamiento para eliminar una tarea.
     */
    private fun attachSwipeToDelete() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            /**
             * Método que maneja el movimiento de un elemento dentro del RecyclerView.
             * En este caso, no se utiliza, así que devuelve false.
             *
             * @param recyclerView El RecyclerView donde ocurre el movimiento.
             * @param viewHolder El ViewHolder del elemento que se está moviendo.
             * @param target El ViewHolder del elemento objetivo donde se está moviendo.
             * @return false porque no se usa el movimiento.
             */
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // No se utiliza el movimiento vertical
            }

            /**
             * Método que se llama para dibujar el ítem mientras se desliza.
             * Cambia el color del fondo del ítem a rojo mientras se desliza.
             *
             * @param c Canvas donde se dibuja el RecyclerView.
             * @param recyclerView El RecyclerView.
             * @param viewHolder El ViewHolder del ítem que se desliza.
             * @param dX Desplazamiento en X durante el deslizamiento.
             * @param dY Desplazamiento en Y durante el deslizamiento.
             * @param actionState Estado actual del gesto (deslizar, arrastrar, etc.).
             * @param isCurrentlyActive Indica si el gesto está activo.
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
                    val itemView = viewHolder.itemView // Obtiene la vista del ítem

                    // Cambia el color del fondo del ítem a rojo mientras se desliza
                    itemView.setBackgroundColor(Color.RED)

                    // Aplica la traducción en X para el desplazamiento
                    itemView.translationX = dX
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            /**
             * Método que se llama cuando un ítem ha sido deslizado.
             * Llama a la función de eliminación después del deslizamiento.
             *
             * @param viewHolder El ViewHolder que fue deslizado.
             * @param direction La dirección en la que se deslizó (izquierda o derecha).
             */
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition // Obtiene la posición del ítem deslizado
                deleteTask(position) // Llama a la función de eliminación
            }

            /**
             * Método que se llama para limpiar la vista del ítem después de que se ha deslizado.
             * Restablece el color de fondo al original.
             *
             * @param recyclerView El RecyclerView donde se encuentra el ítem.
             * @param viewHolder El ViewHolder del ítem que fue deslizado.
             */
            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                // Restablece el color de fondo al original después de que el usuario deja de deslizar
                viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT)
            }
        }

        // Asocia el ItemTouchHelper al RecyclerView
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rvTarea)
    }
}

package com.example.listado5a

import AlumnoAdapter
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listado5a.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: AlumnoAdapter
    private lateinit var data: MutableList<Alumno> // Hacemos la lista mutable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializamos ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializamos la lista de alumnos (mutable)

            data.add(Alumno("Nombre 1", "12345", "email1@example.com", "url_imagen_1"))
            data.add(Alumno("Nombre 2", "67890", "email2@example.com", "url_imagen_2"))

        // Configuramos el RecyclerView con un LinearLayoutManager
        binding.recyclerViewAlumnos.layoutManager = LinearLayoutManager(this)

        // Configuramos el adaptador del RecyclerView
        rvAdapter = AlumnoAdapter(this, data, object : AlumnoAdapter.OptionsMenuClickListener {
            override fun opOptionsMenuClicked(position: Int) {
                itemOptionsMenu(position)
            }
        })

        // Asignamos el adaptador al RecyclerView
        binding.recyclerViewAlumnos.adapter = rvAdapter
        binding.buttomAdd.setOnClickListener {
            val intento1 = Intent(this,MainActivity_Nuevo::class.java)
            //intento1.putExtra("valor1","Hola mundo")
            startActivity(intento1)
        }

//Variable para recibir extras
        val parExtra = intent.extras
        val msje = parExtra?.getString("mensaje")
        val nombre = parExtra?.getString("nombre")
        val cuenta = parExtra?.getString("cuenta")
        val correo = parExtra?.getString("correo")
        val image = parExtra?.getString("image")

//Preguntamos se el mensaje es para nuevo alumno
        if (msje=="nuevo"){
            //Sacamos en una variable el total de elementos en nuestra lista
            val insertIndex: Int = data.count()
            //Usamos la variable insertIndex para indicar la posición del nuevo alumno
            data.add(insertIndex,
                Alumno(
                    "${nombre}",
                    "$cuenta}",
                    "${correo}",
                    "${image}"
                )
            )
            //Notificamos que se inserto un nuevo elemento en la lista
            rvAdapter.notifyItemInserted(insertIndex)
        }



    }



    // Método para manejar el PopupMenu y las acciones de cada opción
    private fun itemOptionsMenu(position: Int) {
        // Creamos un PopupMenu en la posición correspondiente del RecyclerView
        val popupMenu = PopupMenu(this, binding.recyclerViewAlumnos.findViewHolderForAdapterPosition(position)?.itemView?.findViewById(R.id.textViewOptions))
        popupMenu.inflate(R.menu.options_menu)

        // Implementamos el click en las opciones del menú
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.borrar -> {
                        // Eliminamos el alumno de la lista y notificamos al adaptador
                        data.removeAt(position)
                        rvAdapter.notifyItemRemoved(position)
                        return true
                    }

                    R.id.editar -> {
                        // Obtenemos los datos del alumno seleccionado
                        val alumno = data[position]
                        val intento2 =
                            Intent(this@MainActivity, MainActivity_Nuevo::class.java).apply {
                                putExtra("mensaje", "edit")
                                putExtra("nombre", alumno.nombre)
                                putExtra("cuenta", alumno.cuenta)
                                putExtra("correo", alumno.correo)
                                putExtra("imagen", alumno.imagen)
                                putExtra(
                                    "idA",
                                    position
                                ) // Pasamos la posición para identificar el elemento a editar
                            }
                        startActivity(intento2)
                        return true
                    }
                }
                return false
            }
        })

        // Mostramos el PopupMenu
        popupMenu.show()
    }
}

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.listado5a.Alumno
import com.example.listado5a.databinding.ItemAlumnoBinding // Asegúrate de importar el archivo generado de binding

class AlumnoAdapter(private val context: Context, private val listAlumnos: List<Alumno>, private val optionsMenuClickListener: OptionsMenuClickListener) : RecyclerView.Adapter<AlumnoAdapter.ViewHolder>() {

    interface ClickListener {
        fun onItemClick(view: View, position: Int)
    }

    private lateinit var clickListener: ClickListener

    fun setOnItemClickListener(listener: ClickListener) {
        clickListener = listener
    }
    interface OptionsMenuClickListener {
        fun opOptionsMenuClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Utiliza ViewBinding para inflar la vista
        val binding = ItemAlumnoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(listAlumnos[position]){
                Glide.with(context).load(this.imagen).into((binding.imgPersona))
                binding.tvNombre.text = this.nombre
                binding.tvCuenta.text = this.cuenta
                binding.tvCorreo.text = this.correo

                binding.textViewOptions.setOnClickListener{
                    optionsMenuClickListener.opOptionsMenuClicked(position)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return listAlumnos.size
    }

    inner class ViewHolder(val binding: ItemAlumnoBinding) : RecyclerView.ViewHolder(binding.root)
}

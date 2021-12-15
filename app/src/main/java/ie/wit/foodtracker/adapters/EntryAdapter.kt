package ie.wit.foodtracker.adapters



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.foodtracker.databinding.CardEntryBinding
import ie.wit.foodtracker.models.EntryModel
interface EntryListener {
    fun onEntryClick(entry: EntryModel)
}

class EntryAdapter constructor(private var entrys: List<EntryModel>,
                                   private val listener: EntryListener) :
    RecyclerView.Adapter<EntryAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardEntryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val entry = entrys[holder.adapterPosition]
        holder.bind(entry, listener)
    }

    override fun getItemCount(): Int = entrys.size

    class MainHolder(private val binding : CardEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: EntryModel, listener: EntryListener) {
            binding.entryTitle.text = entry.title
            binding.description.text = entry.description
            binding.kcal.text = entry.kcal.toString()
            binding.date.text = entry.date
            binding.time.text = entry.time
            Picasso.get().load(entry.image).resize(200,200).into(binding.imageIcon)





            binding.root.setOnClickListener { listener.onEntryClick(entry) }
        }
    }
}
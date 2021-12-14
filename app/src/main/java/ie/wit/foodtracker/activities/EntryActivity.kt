package ie.wit.foodtracker
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.foodtracker.databinding.ActivityEntryBinding
import timber.log.Timber
import timber.log.Timber.i

class EntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Entry Activity started...")

        binding.btnAdd.setOnClickListener() {
            val entryTitle = binding.entryTitle.text.toString()
            if (entryTitle.isNotEmpty()) {
                i("add Button Pressed: $entryTitle")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}
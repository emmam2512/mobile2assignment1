package ie.wit.foodtracker.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.wit.foodtracker.R
import ie.wit.foodtracker.databinding.ActivityEntryBinding
import ie.wit.foodtracker.main.MainApp
import timber.log.Timber
import timber.log.Timber.i
import ie.wit.foodtracker.models.EntryModel

class EntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEntryBinding
    var entry = EntryModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        i("Entry Activity started...")
        if (intent.hasExtra("entry_edit")) {
            entry = intent.extras?.getParcelable("entry_edit")!!
            binding.entryTitle.setText(entry.title)
            binding.description.setText(entry.description)
            binding.kcal.setText(entry.kcal.toString())
            binding.date.setText(entry.date)
            binding.time.setText(entry.time)

        }
        binding.btnAdd.setOnClickListener() {
            entry.title = binding.entryTitle.text.toString()
            entry.description = binding.description.text.toString()
            entry.kcal = binding.kcal.text.toString().toInt()
            entry.date = binding.date.text.toString()
            entry.time = binding.time.text.toString()
            if (entry.title.isNotEmpty()) {
                app.entrys.create(entry.copy())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_entry, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
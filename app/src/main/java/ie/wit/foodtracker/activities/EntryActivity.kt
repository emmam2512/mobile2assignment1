package ie.wit.foodtracker.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.foodtracker.R
import ie.wit.foodtracker.databinding.ActivityEntryBinding
import ie.wit.foodtracker.helpers.showImagePicker
import ie.wit.foodtracker.main.MainApp
import timber.log.Timber
import timber.log.Timber.i
import ie.wit.foodtracker.models.EntryModel

class EntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEntryBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var entry = EntryModel()
    var edit = false
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
            edit = true
            entry = intent.extras?.getParcelable("entry_edit")!!
            binding.entryTitle.setText(entry.title)
            binding.description.setText(entry.description)
            binding.kcal.setText(entry.kcal.toString())
            binding.date.setText(entry.date)
            binding.time.setText(entry.time)
            binding.btnAdd.setText(R.string.save_entry)
            Picasso.get()
                .load(entry.image)
                .into(binding.entryImage)

        }
        binding.btnAdd.setOnClickListener() {
            entry.title = binding.entryTitle.text.toString()
            entry.description = binding.description.text.toString()
            entry.kcal = binding.kcal.text.toString().toInt()
            entry.date = binding.date.text.toString()
            entry.time = binding.time.text.toString()

            if (entry.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_entry_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.entrys.update(entry.copy())
                } else {
                    app.entrys.create(entry.copy())
                }
            }
            i("add Button Pressed: $entry")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            i("Select image")
        }
        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        registerImagePickerCallback()
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
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            entry.image = result.data!!.data!!
                            Picasso.get()
                                .load(entry.image)
                                .into(binding.entryImage)
                            binding.chooseImage.setText(R.string.change_entry_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}
package ie.wit.foodtracker.activities
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Switch
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.snackbar.Snackbar

import com.squareup.picasso.Picasso
import ie.wit.foodtracker.R
import ie.wit.foodtracker.databinding.ActivityEntryBinding
import ie.wit.foodtracker.helpers.showImagePicker
import ie.wit.foodtracker.main.MainApp
import timber.log.Timber
import timber.log.Timber.i
import ie.wit.foodtracker.models.EntryModel
import ie.wit.foodtracker.models.Location
import org.wit.entry.activities.MapActivity

class EntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEntryBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>

    // var location = Location(52.245696, -7.139102, 15f)
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
                Snackbar.make(it, R.string.enter_entry_title, Snackbar.LENGTH_LONG)
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
        binding.btnDelete.setOnClickListener() {
            app.entrys.delete(entry.copy())
            i("Delete Button Pressed: $entry")
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

        binding.entryLocation.setOnClickListener {
            i("Set Location Pressed")
        }
        registerMapCallback()


        binding.entryLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (entry.zoom != 0f) {
                location.lat = entry.lat
                location.lng = entry.lng
                location.zoom = entry.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
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

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location =
                                result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            entry.lat = location.lat
                            entry.lng = location.lng
                            entry.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
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
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
}



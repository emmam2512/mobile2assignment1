package ie.wit.foodtracker.activities

//import android.content.Intent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Switch
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.foodtracker.R
import ie.wit.foodtracker.adapters.EntryAdapter
import ie.wit.foodtracker.adapters.EntryListener
import ie.wit.foodtracker.databinding.CardEntryBinding
import ie.wit.foodtracker.databinding.ActivityEntryListBinding
import ie.wit.foodtracker.main.MainApp
import ie.wit.foodtracker.models.EntryModel


class EntryListActivity : AppCompatActivity(), EntryListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityEntryListBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityEntryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        binding.btnChangeTheme.setOnClickListener { chooseThemeDialog() }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.searchBtn.setOnClickListener {
            showEntrys(app.entrys.findByTitle(binding.searchText.text.toString()))
        }

        loadEntrys()

        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, EntryActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onEntryClick(entry: EntryModel) {
        val launcherIntent = Intent(this, EntryActivity::class.java)
        launcherIntent.putExtra("entry_edit", entry)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadEntrys() }
    }

    private fun loadEntrys() {
        showEntrys(app.entrys.findAll())
    }

    fun showEntrys(entrys: List<EntryModel>) {
        binding.recyclerView.adapter = EntryAdapter(entrys, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.choose_theme_text))
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = 0

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    delegate.applyDayNight()

                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

            }
        }

        val dialog = builder.create()
        dialog.show()
    }
}
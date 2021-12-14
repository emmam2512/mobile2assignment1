package ie.wit.foodtracker.activities

//import android.content.Intent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = EntryAdapter(app.entrys.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, EntryActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onEntryClick(entry: EntryModel) {
        val launcherIntent = Intent(this, EntryActivity::class.java)
        launcherIntent.putExtra("entry_edit", entry)
        startActivityForResult(launcherIntent,0)
    }
}
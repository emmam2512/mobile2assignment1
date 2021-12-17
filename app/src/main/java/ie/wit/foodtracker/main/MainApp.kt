package ie.wit.foodtracker.main
import android.app.Application
import ie.wit.foodtracker.models.EntryJSONStore
import ie.wit.foodtracker.models.EntryMemStore
import ie.wit.foodtracker.models.EntryModel
import ie.wit.foodtracker.models.EntryStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var entrys: EntryStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        
        entrys = EntryJSONStore(applicationContext)
        i("Entry started")
    }
}

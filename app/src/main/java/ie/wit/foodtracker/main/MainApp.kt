package ie.wit.foodtracker.main
import android.app.Application
import ie.wit.foodtracker.models.EntryMemStore
import ie.wit.foodtracker.models.EntryModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

   // val entrys = ArrayList<EntryModel>()
   val entrys = EntryMemStore()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Entry started")
        //entrys.add(EntryModel("One", "About one..."))
       // entrys.add(EntryModel("Two", "About two..."))
      //  entrys.add(EntryModel("Three", "About three..."))
    }
}
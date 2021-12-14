package ie.wit.foodtracker.models

import timber.log.Timber.i

class EntryMemStore : EntryStore {

    val entrys = ArrayList<EntryModel>()

    override fun findAll(): List<EntryModel> {
        return entrys
    }

    override fun create(entry: EntryModel) {
        entrys.add(entry)
        logAll()
    }

    fun logAll() {
        entrys.forEach{ i("${it}") }
    }
}
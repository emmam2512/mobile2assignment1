package ie.wit.foodtracker.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class EntryMemStore : EntryStore {

    val entrys = ArrayList<EntryModel>()

    override fun findAll(): List<EntryModel> {
        return entrys
    }

    override fun create(entry: EntryModel) {
        entry.id = getId()
        entrys.add(entry)
        logAll()
    }
    override fun findByTitle(title: String): ArrayList<EntryModel> {
        val foundEntrys = ArrayList<EntryModel>()
        entrys.forEach() { p -> p.title; if(p.title == title){
            foundEntrys.add(p)
        } }

        return foundEntrys
    }


    override fun update(entry: EntryModel) {
        var foundEntry: EntryModel? = entrys.find { p -> p.id == entry.id }
        if (foundEntry != null) {
            foundEntry.title = entry.title
            foundEntry.description = entry.description
            foundEntry.kcal = entry.kcal
            foundEntry.date = entry.date
            foundEntry.time = entry.time
            foundEntry.image = entry.image

            logAll()
        }
    }

    private fun logAll() {
        entrys.forEach { i("$it") }
    }
}
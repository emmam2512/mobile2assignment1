package ie.wit.foodtracker.models



interface EntryStore {
    fun findAll(): List<EntryModel>
    fun create(entry: EntryModel)
    fun update(entry: EntryModel)
    fun findByTitle(title: String): ArrayList<EntryModel>
    fun delete(entry: EntryModel)

}
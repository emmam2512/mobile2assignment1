package ie.wit.foodtracker.models



interface EntryStore {
    fun findAll(): List<EntryModel>
    fun create(entry: EntryModel)
}
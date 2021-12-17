package ie.wit.foodtracker.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.wit.foodtracker.helpers.exists
import ie.wit.foodtracker.helpers.read
import ie.wit.foodtracker.helpers.write
import ie.wit.foodtracker.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

const val JSON_FILE = "entrys.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<EntryModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class EntryJSONStore(private val context: Context) : EntryStore {

    var entrys = mutableListOf<EntryModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<EntryModel> {
        logAll()
        return entrys
    }

    override fun create(entry: EntryModel) {
        entry.id = generateRandomId()
        entrys.add(entry)
        serialize()
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
            foundEntry.lat = entry.lat
            foundEntry.lng = entry.lng
            foundEntry.zoom = entry.zoom

            logAll()
        }
    }
    override fun delete(entry: EntryModel) {
        entrys.remove(entry)
        serialize()
    }
    override fun findByTitle(title: String): ArrayList<EntryModel> {
        val foundEntrys = ArrayList<EntryModel>()
        entrys.forEach() { p -> p.title; if(p.title == title){
            foundEntrys.add(p)
        } }

        return foundEntrys
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(entrys, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        entrys = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        entrys.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }

}
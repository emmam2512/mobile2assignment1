package ie.wit.foodtracker.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
@Parcelize
data class EntryModel(var id: Long = 0,
                          var title: String = "",
                          var description: String = "",
                          var kcal: Int = 0 ,
                          var date: String = "",
                          var time: String = "",
                          var image: Uri = Uri.EMPTY,
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f) : Parcelable
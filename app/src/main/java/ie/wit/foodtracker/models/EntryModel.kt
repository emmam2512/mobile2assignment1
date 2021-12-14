package ie.wit.foodtracker.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EntryModel(var title: String = "",
                      var description: String = "",
                      var kcal: Int = 0 ,
                      var date: String = "",
                      var time: String = "") : Parcelable
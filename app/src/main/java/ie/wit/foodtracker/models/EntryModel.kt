package ie.wit.foodtracker.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class EntryModel(var id: Long = 0,
                      var title: String = "",
                      var description: String = "",
                      var kcal: Int = 0 ,
                      var date: String = "",
                      var time: String = "",
                      var image: Uri = Uri.EMPTY) : Parcelable
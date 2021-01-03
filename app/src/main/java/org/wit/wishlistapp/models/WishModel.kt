package org.wit.wishlistapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class WishModel (
    var id: Long = 0,
    var name: String = "",
    var time: String = "",
    var description : String = "",
    var image: String = ""
) : Parcelable

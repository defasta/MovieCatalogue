package com.example.moviecatalogue2.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShow(
    var id: Int = 0,
    var photo: String? = null,
    var name: String? = null,
    var description: String? = null,
    var year: String? = null,
    var score: String? = null

) : Parcelable

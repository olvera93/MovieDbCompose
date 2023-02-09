package com.olvera.moviedbcompose.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieGenre(
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String
): Parcelable

package com.olvera.moviedbcompose.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieVideo(
    @SerializedName("id")
    var id: String,

    @SerializedName("key")
    var key: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("site")
    var site: String
): Parcelable

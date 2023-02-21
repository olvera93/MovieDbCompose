package com.olvera.moviedbcompose.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(

    @PrimaryKey
    @SerializedName("id")
    var movieId: Int,

    @SerializedName("poster_path")
    var poster_path: String? = null,

    @SerializedName("overview")
    var overview: String? = null,

    @SerializedName("release_date")
    var release_date: String? = null,

    @SerializedName("original_title")
    var original_title: String? = null,

    @SerializedName("original_language")
    var original_language: String? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("backdrop_path")
    var backdrop_path: String? = null,

    @SerializedName("popularity")
    var popularity: Double,

    @SerializedName("vote_count")
    var vote_count: Int,

    @SerializedName("video")
    var video: Boolean,

    @SerializedName("vote_average")
    var vote_average: Double,

    ) : Parcelable
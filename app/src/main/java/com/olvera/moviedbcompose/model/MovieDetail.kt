package com.olvera.moviedbcompose.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
    @SerializedName("id")
    var id: Int,

    @SerializedName("backdrop_path")
    var backdrop_path: String? = null,

    @SerializedName("overview")
    var overview: String? = null,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    var poster_path: String? = null,

    @SerializedName("release_date")
    var release_date: String? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("genres")
    var genres: List<MovieGenre>? = null,

    @SerializedName("video")
    var video: Boolean,

    @SerializedName("vote_average")
    var vote_average: Double,

    @SerializedName("vote_count")
    var vote_count: Int,

    @SerializedName("homepage")
    val homepage: String?

) : Parcelable

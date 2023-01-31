package com.olvera.moviedbcompose.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResult(
    @SerializedName("results")
    val results: List<Movie>
): Parcelable

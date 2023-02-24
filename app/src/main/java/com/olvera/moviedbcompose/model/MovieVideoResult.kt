package com.olvera.moviedbcompose.model

import com.google.gson.annotations.SerializedName

data class MovieVideoResult(
    @SerializedName("results")
    var results: List<MovieVideo> = listOf()
)

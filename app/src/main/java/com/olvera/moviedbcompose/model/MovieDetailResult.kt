package com.olvera.moviedbcompose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailResult(
    val movieDetail: MovieDetail? = null,
): Parcelable

package com.olvera.moviedbcompose.data

import com.olvera.moviedbcompose.model.MovieResult
import com.olvera.moviedbcompose.util.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getMoviePopular(
        @Query(API_KEY) apiKey: String
    ): MovieResult

}
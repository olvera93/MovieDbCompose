package com.olvera.moviedbcompose.data

import com.olvera.moviedbcompose.model.Movie
import com.olvera.moviedbcompose.model.MovieResult
import com.olvera.moviedbcompose.util.Constants.Companion.QUERY_API_KEY
import com.olvera.moviedbcompose.util.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getMoviePopular(
        @Query(QUERY_API_KEY) apiKey: String
    ): MovieResult

}
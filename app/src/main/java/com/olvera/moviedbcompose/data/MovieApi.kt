package com.olvera.moviedbcompose.data

import com.olvera.moviedbcompose.model.MovieDetail
import com.olvera.moviedbcompose.model.MovieDetailResult
import com.olvera.moviedbcompose.model.MovieResult
import com.olvera.moviedbcompose.util.Constants.Companion.PATH_MOVIE_ID
import com.olvera.moviedbcompose.util.Constants.Companion.QUERY_API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getMoviePopular(
        @Query(QUERY_API_KEY) apiKey: String
    ): MovieResult

    @GET("movie/upcoming")
    suspend fun getMovieUpcoming(
        @Query(QUERY_API_KEY) apiKey: String
    ): MovieResult

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path(PATH_MOVIE_ID) movieId: Int,
        @Query(QUERY_API_KEY) api: String
    ): MovieDetailResult

}
package com.olvera.moviedbcompose.data.remote

import com.olvera.moviedbcompose.model.*
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
    ): MovieDetail

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path(PATH_MOVIE_ID) movieId: Int,
        @Query(QUERY_API_KEY) api: String
    ): MovieVideoResult

}
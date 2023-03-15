package com.olvera.moviedbcompose.services

import com.olvera.moviedbcompose.data.remote.MovieApi
import com.olvera.moviedbcompose.model.Movie
import com.olvera.moviedbcompose.model.MovieDetail
import com.olvera.moviedbcompose.model.MovieResult
import com.olvera.moviedbcompose.model.MovieVideoResult

class FakeMovieService : MovieApi {
    override suspend fun getMoviePopular(apiKey: String): MovieResult {

        return MovieResult(
            listOf(
                Movie(
                    movieId = 1,
                    poster_path = "poster_path",
                    overview = "overview",
                    release_date = "release_date",
                    original_title = "original_title",
                    original_language = "original_language",
                    title = "title",
                    backdrop_path = "backdrop_path",
                    popularity = 1.0,
                    vote_count = 1,
                    video = false,
                    vote_average = 1.0
                ),
            )
        )

    }

    override suspend fun getMovieUpcoming(apiKey: String): MovieResult {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetail(movieId: Int, api: String): MovieDetail {

        return MovieDetail(
            id = 1,
            poster_path = "poster_path",
            overview = "overview",
            release_date = "release_date",
            original_title = "original_title",
            original_language = "original_language",
            title = "title",
            backdrop_path = "backdrop_path",
            popularity = 1.0,
            vote_count = 1,
            genres = listOf(),
            video = false,
            vote_average = 1.0,
            homepage = "homepage",
            runtime = 1
        )

    }

    override suspend fun getMovieVideos(movieId: Int, api: String): MovieVideoResult {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovie(apiKey: String, query: String): MovieResult {
        TODO("Not yet implemented")
    }

}
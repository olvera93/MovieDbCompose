package com.olvera.moviedbcompose.services

import com.olvera.moviedbcompose.data.remote.MovieApi
import com.olvera.moviedbcompose.model.*

class FakeMovieService : MovieApi {
    override suspend fun getMoviePopular(apiKey: String): MovieResult {

        if (apiKey == "1") {
            error("Movie not found")
        }

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
        if (apiKey == "1") {
            error("Movie not found")
        }

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

    override suspend fun getMovieDetail(movieId: Int, api: String): MovieDetail {

        if (movieId == 1) {
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
        } else {
            error("Movie not found")
        }
    }

    override suspend fun getMovieVideos(movieId: Int, api: String): MovieVideoResult {

        if (movieId != 1) {
            error("Video not found")
        }

        return MovieVideoResult(
            listOf(
                MovieVideo(
                    id = "1",
                    key = "key",
                    name = "name",
                    site = "site"
                )
            )
        )
    }

    override suspend fun searchMovie(apiKey: String, query: String): MovieResult {

        if (query != "movie") {
            error("Movie not found")
        }

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

                Movie(
                    movieId = 2,
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
}
package com.olvera.moviedbcompose.services

import com.olvera.moviedbcompose.data.remote.MovieTask
import com.olvera.moviedbcompose.model.*
import com.olvera.moviedbcompose.util.NetworkResult

class FakeMovieRepository : MovieTask {
    override suspend fun getMovies(apiKey: String): NetworkResult<MovieResult> {

        return if (apiKey != "1") {
            NetworkResult.Error("Movie not found")
        } else

            NetworkResult.Success(
                MovieResult(
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
            )

    }

    override suspend fun getMovieDetail(movieId: Int, apiKey: String): NetworkResult<MovieDetail> {

        return if (movieId != 1) {
            NetworkResult.Error("Movie not found")
        } else

            NetworkResult.Success(
                MovieDetail(
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
            )
    }

    override suspend fun getMovieVideos(
        movieId: Int,
        apiKey: String
    ): NetworkResult<MovieVideoResult> {

        return NetworkResult.Success(
            MovieVideoResult(
                listOf(
                    MovieVideo(
                        id = "1",
                        key = "key",
                        name = "name",
                        site = "site"
                    )
                )
            )
        )

    }

    override suspend fun searchMovie(apiKey: String, query: String): NetworkResult<MovieResult> {

        return NetworkResult.Success(
            MovieResult(
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
        )
    }

    override suspend fun addMovieToRoom(movie: Movie) {
        return Unit
    }

    override suspend fun deleteMovieToRoom(movie: Movie) {
        return Unit
    }

    override suspend fun getMovieById(movieId: Int): Movie {

        return Movie(
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
        )

    }

    override suspend fun getMoviesFromRoom(): List<Movie> {
        val movie1 = Movie(
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
        )

        val listOfMovies: List<Movie> = listOf(movie1)

        return listOfMovies.ifEmpty {
            listOf()
        }
    }
}
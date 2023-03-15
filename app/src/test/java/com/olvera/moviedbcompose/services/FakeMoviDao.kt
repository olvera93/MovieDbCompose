package com.olvera.moviedbcompose.services

import com.olvera.moviedbcompose.data.room.MovieDao
import com.olvera.moviedbcompose.model.Movie

class FakeMovieDao : MovieDao {
    override suspend fun insertMovie(movie: Movie) {
        // Insert movie
        return Unit as Unit

    }

    override fun getMovies(): List<Movie> {

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

    override fun getMovie(movieId: Int): Movie {
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

    override fun deleteMovie(movie: Movie) {

    }

}
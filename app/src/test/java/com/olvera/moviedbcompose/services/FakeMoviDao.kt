package com.olvera.moviedbcompose.services

import com.olvera.moviedbcompose.data.room.MovieDao
import com.olvera.moviedbcompose.model.Movie

class FakeMovieDao : MovieDao {
    override suspend fun insertMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

    override fun getMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    override fun getMovie(movieId: Int): Movie {
        TODO("Not yet implemented")
    }

    override fun deleteMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

}
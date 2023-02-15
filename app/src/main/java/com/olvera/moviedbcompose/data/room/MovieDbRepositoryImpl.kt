package com.olvera.moviedbcompose.data.room

import com.olvera.moviedbcompose.model.Movie

class MovieDbRepositoryImpl(
    private val movieDao: MovieDao
) : MovieDbRepository {
    override fun getMovieFromRoom() = movieDao.getMovies()
    override fun addMovieToRoom(movie: Movie) = movieDao.insertMovie(movie)

}
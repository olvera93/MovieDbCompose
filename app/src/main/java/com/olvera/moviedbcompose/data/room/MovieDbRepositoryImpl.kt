package com.olvera.moviedbcompose.data.room

import com.olvera.moviedbcompose.model.Movie
import javax.inject.Inject

class MovieDbRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieDbRepository {
    override suspend fun getMovieFromRoom() = movieDao.getMovies()
    override suspend fun addMovieToRoom(movie: Movie) = movieDao.insertMovie(movie)

}
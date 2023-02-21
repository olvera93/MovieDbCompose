package com.olvera.moviedbcompose.data.room

import com.olvera.moviedbcompose.model.Movie

typealias Movies = List<Movie>
interface MovieDbRepository {

    suspend fun getMovieFromRoom(): List<Movie>
    suspend fun addMovieToRoom(movie: Movie)
}
package com.olvera.moviedbcompose.data.room

import com.olvera.moviedbcompose.model.Movie

typealias Movies = List<Movie>
interface MovieDbRepository {

    fun getMovieFromRoom(): List<Movie>
    fun addMovieToRoom(movie: Movie)
}
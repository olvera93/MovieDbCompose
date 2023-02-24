package com.olvera.moviedbcompose.data.room

import androidx.room.*
import com.olvera.moviedbcompose.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("SELECT * FROM movies")
    fun getMovies(): List<Movie>

    @Query("SELECT * FROM movies WHERE movieId = :movieId")
    fun getMovie(movieId: Int): Movie

    @Delete
    fun deleteMovie(movie: Movie)

}
package com.olvera.moviedbcompose.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.olvera.moviedbcompose.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
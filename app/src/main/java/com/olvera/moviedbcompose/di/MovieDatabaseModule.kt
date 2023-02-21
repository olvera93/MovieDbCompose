package com.olvera.moviedbcompose.di

import android.content.Context
import androidx.room.Room
import com.olvera.moviedbcompose.data.remote.MovieRepository
import com.olvera.moviedbcompose.data.remote.MovieTask
import com.olvera.moviedbcompose.data.room.MovieDao
import com.olvera.moviedbcompose.data.room.MovieDatabase
import com.olvera.moviedbcompose.data.room.MovieDbRepository
import com.olvera.moviedbcompose.data.room.MovieDbRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MovieDatabaseModule {

    @Provides
    fun provideMovieDb(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, MovieDatabase::class.java, "MovieDBAppDataBase"
    ).build()


    @Provides
    fun provideMovieDao(db: MovieDatabase) = db.movieDao()

    @Provides
    fun provideMovieRepository(
        movieDao: MovieDao
    ) = MovieDbRepositoryImpl(movieDao)

}
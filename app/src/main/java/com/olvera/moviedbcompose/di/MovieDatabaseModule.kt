package com.olvera.moviedbcompose.di

import android.content.Context
import androidx.room.Room
import com.olvera.moviedbcompose.data.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDatabaseModule {

    @Provides
    fun provideMovieDb(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, MovieDatabase::class.java, "movies_db"
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDatabase) = db.movieDao()

}
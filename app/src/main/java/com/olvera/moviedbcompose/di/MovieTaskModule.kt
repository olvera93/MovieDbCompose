package com.olvera.moviedbcompose.di

import com.olvera.moviedbcompose.data.MovieRepository
import com.olvera.moviedbcompose.data.MovieTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieTaskModule {

    @Binds
    abstract fun bindMovieTask(
        movieRepository: MovieRepository
    ): MovieTask
}
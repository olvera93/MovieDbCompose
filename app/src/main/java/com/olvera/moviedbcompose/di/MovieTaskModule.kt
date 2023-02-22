package com.olvera.moviedbcompose.di

import com.olvera.moviedbcompose.data.remote.MovieRepository
import com.olvera.moviedbcompose.data.remote.MovieTask
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
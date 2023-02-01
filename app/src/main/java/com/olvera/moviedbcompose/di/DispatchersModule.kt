package com.olvera.moviedbcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @IoDispatcher
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun provideMainDispatcher() = Dispatchers.Main

    annotation class MainDispatcher

    annotation class IoDispatcher
}
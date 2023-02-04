package com.olvera.moviedbcompose.data

import com.olvera.moviedbcompose.di.DispatchersModule
import com.olvera.moviedbcompose.model.Movie
import com.olvera.moviedbcompose.model.MovieResult
import com.olvera.moviedbcompose.util.NetworkResult
import com.olvera.moviedbcompose.util.makeNetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MovieTask {

    suspend fun getMoviePopular(
        apiKey: String
    ): NetworkResult<MovieResult>
}


class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    @DispatchersModule.IoDispatcher private val dispatcher: CoroutineDispatcher
): MovieTask {

    override suspend fun getMoviePopular(apiKey: String): NetworkResult<MovieResult> {
        return withContext(dispatcher) {
            downloadMovie(apiKey)
        }
    }


    private suspend fun downloadMovie(apiKey: String): NetworkResult<MovieResult> = makeNetworkCall {
        movieApi.getMoviePopular(apiKey)
    }


}
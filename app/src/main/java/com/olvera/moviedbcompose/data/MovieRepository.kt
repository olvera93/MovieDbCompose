package com.olvera.moviedbcompose.data

import com.olvera.moviedbcompose.di.DispatchersModule
import com.olvera.moviedbcompose.model.MovieResult
import com.olvera.moviedbcompose.util.NetworkResult
import com.olvera.moviedbcompose.util.makeNetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MovieTask {

    suspend fun getMovies(
        apiKey: String
    ): NetworkResult<MovieResult>

}

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    @DispatchersModule.IoDispatcher private val dispatcher: CoroutineDispatcher
) : MovieTask {
    override suspend fun getMovies(apiKey: String): NetworkResult<MovieResult> {
        return withContext(dispatcher) {
            val popular = async { downloadMoviePopular(apiKey) }
            val upcoming = async { downloadMovieUpcoming(apiKey) }
            val popularResult = popular.await()
            val upcomingResult = upcoming.await()
            if (popularResult is NetworkResult.Success && upcomingResult is NetworkResult.Success) {
                NetworkResult.Success(
                    MovieResult(
                        popularResult.data.results + upcomingResult.data.results
                    )
                )
            } else {
                NetworkResult.Error("Error")
            }
        }
    }

    private suspend fun downloadMovieUpcoming(apiKey: String): NetworkResult<MovieResult> =
        makeNetworkCall {
            movieApi.getMovieUpcoming(apiKey)
        }

    private suspend fun downloadMoviePopular(apiKey: String): NetworkResult<MovieResult> =
        makeNetworkCall {
            movieApi.getMoviePopular(apiKey)
        }


}
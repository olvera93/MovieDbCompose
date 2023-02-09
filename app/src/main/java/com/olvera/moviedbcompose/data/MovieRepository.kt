package com.olvera.moviedbcompose.data

import android.util.Log
import com.olvera.moviedbcompose.di.DispatchersModule
import com.olvera.moviedbcompose.model.MovieDetail
import com.olvera.moviedbcompose.model.MovieDetailResult
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

    suspend fun getMovieDetail(
        movieId: Int,
        apiKey: String
    ): NetworkResult<MovieDetailResult>

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

    override suspend fun getMovieDetail(
        movieId: Int,
        apiKey: String
    ): NetworkResult<MovieDetailResult> {
        return withContext(dispatcher) {
            val movieDetail = async { downloadMovieDetail(movieId, apiKey) }
            val movieDetailResult = movieDetail.await()
            if (movieDetailResult is NetworkResult.Success) {
                NetworkResult.Success(movieDetailResult.data)
            } else {
                NetworkResult.Error("Error")
            }
        }
    }

    private suspend fun downloadMovieDetail(movieId: Int, apiKey: String): NetworkResult<MovieDetailResult> =
        makeNetworkCall {
            movieApi.getMovieDetail(movieId, apiKey)
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
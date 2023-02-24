package com.olvera.moviedbcompose.data.remote

import com.olvera.moviedbcompose.data.room.MovieDao
import com.olvera.moviedbcompose.model.*
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
    ): NetworkResult<MovieDetail>

    suspend fun getMovieVideos(
        movieId: Int,
        apiKey: String
    ): NetworkResult<MovieVideoResult>

    suspend fun addMovieToRoom(movie: Movie)

    suspend fun deleteMovieToRoom(movie: Movie)

    suspend fun getMovieById(movieId: Int): Movie


}

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val dispatcher: CoroutineDispatcher
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
    ): NetworkResult<MovieDetail> {
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

    override suspend fun getMovieVideos(
        movieId: Int,
        apiKey: String
    ): NetworkResult<MovieVideoResult> {
        return withContext(dispatcher) {
            val movieVideos = async { downloadMovieVideos(movieId, apiKey) }
            val movieVideosResult = movieVideos.await()
            if (movieVideosResult is NetworkResult.Success) {
                NetworkResult.Success(movieVideosResult.data)
            } else {
                NetworkResult.Error("Error")
            }
        }
    }

    override suspend fun addMovieToRoom(movie: Movie) = movieDao.insertMovie(movie)
    override suspend fun deleteMovieToRoom(movie: Movie) = movieDao.deleteMovie(movie)
    override suspend fun getMovieById(movieId: Int): Movie = movieDao.getMovie(movieId)

    private suspend fun downloadMovieVideos(
        movieId: Int,
        apiKey: String
    ): NetworkResult<MovieVideoResult> =
        makeNetworkCall {
            movieApi.getMovieVideos(movieId, apiKey)
        }

    private suspend fun downloadMovieDetail(
        movieId: Int,
        apiKey: String
    ): NetworkResult<MovieDetail> =
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
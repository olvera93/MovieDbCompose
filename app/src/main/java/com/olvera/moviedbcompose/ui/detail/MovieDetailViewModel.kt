package com.olvera.moviedbcompose.ui.detail

import android.os.Parcelable
import androidx.compose.runtime.mutableStateOf
import com.olvera.moviedbcompose.data.remote.MovieTask
import com.olvera.moviedbcompose.data.room.MovieDbRepository
import com.olvera.moviedbcompose.data.room.MovieDbRepositoryImpl
import com.olvera.moviedbcompose.model.*
import com.olvera.moviedbcompose.ui.MovieViewModel
import com.olvera.moviedbcompose.util.Constants
import com.olvera.moviedbcompose.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.parcelize.Parcelize

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieTask,
    private val movieDbRepository: MovieDbRepositoryImpl
) : MovieViewModel() {

    var movieDetailResponse = mutableStateOf(MovieDetailResult())
        private set

    var movieRoom = mutableStateOf(MovieResult())
        private set

    var status = mutableStateOf<NetworkResult<Any>?>(null)
        private set

    var movieVideoResponse = mutableStateOf<List<MovieVideo>>(listOf())
        private set

    fun addMovieToRoom(movie: Movie) {
        launchCatching {
            movieDbRepository.addMovieToRoom(movie)
            println("Movie added to room ${movie.title}")
        }
    }

    fun getMovieDetail(movieId: Int) {
        launchCatching {
            status.value = NetworkResult.Loading()
            val response = movieRepository.getMovieDetail(movieId, Constants.API_KEY)
            status.value = NetworkResult.Success(response)
            handleNetworkResponse(response)
        }
    }

    fun getMovieVideo(movieId: Int) {
        launchCatching {
            status.value = NetworkResult.Loading()
            val response = movieRepository.getMovieVideos(movieId, Constants.API_KEY)
            status.value = NetworkResult.Success(response)
            handleNetworkResponseVideo(response)
        }
    }

    private fun handleNetworkResponseVideo(networkResult: NetworkResult<MovieVideoResult>) {
        when (networkResult) {
            is NetworkResult.Success -> {
                movieVideoResponse.value = networkResult.data.results
            }
            is NetworkResult.Error -> {
                status.value = NetworkResult.Error(networkResult.message)
            }
            is NetworkResult.Loading -> {
                status.value = NetworkResult.Loading()
            }
        }
    }


    private fun handleNetworkResponse(networkResult: NetworkResult<MovieDetail>) {
        when (networkResult) {
            is NetworkResult.Success -> {
                movieDetailResponse.value = MovieDetailResult(networkResult.data)
            }
            is NetworkResult.Error -> {
                status.value = NetworkResult.Error(networkResult.message)
            }
            is NetworkResult.Loading -> {
                status.value = NetworkResult.Loading()
            }
        }
    }

}

@Parcelize
data class MovieResult(
    val movie: Movie? = null
) : Parcelable
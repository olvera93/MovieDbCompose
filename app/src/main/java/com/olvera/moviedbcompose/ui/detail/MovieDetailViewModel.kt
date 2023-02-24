package com.olvera.moviedbcompose.ui.detail

import android.os.Parcelable
import androidx.compose.runtime.mutableStateOf
import com.olvera.moviedbcompose.data.remote.MovieTask
import com.olvera.moviedbcompose.model.*
import com.olvera.moviedbcompose.ui.MovieViewModel
import com.olvera.moviedbcompose.util.Constants
import com.olvera.moviedbcompose.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.parcelize.Parcelize

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieTask
) : MovieViewModel() {

    var movieDetailResponse = mutableStateOf(MovieDetailResult())
        private set

    var status = mutableStateOf<NetworkResult<Any>?>(null)
        private set

    var movieVideoResponse = mutableStateOf<List<MovieVideo>>(listOf())
        private set

    fun addMovieToRoom(movie: Movie) {
        launchCatching {
            status.value = NetworkResult.Loading()
            movieRepository.addMovieToRoom(movie)
        }
    }

    fun deleteMovieToRoom(movie: Movie) {
        launchCatching {
            status.value = NetworkResult.Loading()
            movieRepository.deleteMovieToRoom(movie)
        }
    }

    fun getMovieById(movieId: Int): Boolean {
        var isMovie = false
        launchCatching {
            status.value = NetworkResult.Loading()
            val movie = movieRepository.getMovieById(movieId)
            if (movie.movieId == movieId) {
                isMovie = true
            }
        }
        return isMovie
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
data class MovieRoomResult(
    val movie: Movie? = null
) : Parcelable
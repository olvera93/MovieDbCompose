package com.olvera.moviedbcompose.ui.home

import androidx.compose.runtime.mutableStateOf
import com.olvera.moviedbcompose.data.remote.MovieTask
import com.olvera.moviedbcompose.model.Movie
import com.olvera.moviedbcompose.model.MovieResult
import com.olvera.moviedbcompose.ui.MovieViewModel
import com.olvera.moviedbcompose.util.Constants
import com.olvera.moviedbcompose.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieTask
) : MovieViewModel() {

    var movieResponse = mutableStateOf<List<Movie>>(listOf())
        private set

    var status = mutableStateOf<NetworkResult<Any>?>(null)
        private set

    init {
        getMovies()
    }

     fun getMovies() {
        launchCatching {
            status.value = NetworkResult.Loading()
            val response = movieRepository.getMovies(Constants.API_KEY)
            status.value = NetworkResult.Success(response)
            handleNetworkResponse(response)
        }
    }

    private fun handleNetworkResponse(networkResult: NetworkResult<MovieResult>) {
        when (networkResult) {
            is NetworkResult.Success -> {
                movieResponse.value = networkResult.data.results
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
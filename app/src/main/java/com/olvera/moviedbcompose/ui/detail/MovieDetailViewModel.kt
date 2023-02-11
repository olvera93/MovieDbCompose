package com.olvera.moviedbcompose.ui.detail

import androidx.compose.runtime.mutableStateOf
import com.olvera.moviedbcompose.data.MovieTask
import com.olvera.moviedbcompose.ui.MovieViewModel
import com.olvera.moviedbcompose.util.Constants
import com.olvera.moviedbcompose.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.olvera.moviedbcompose.model.MovieDetail
import com.olvera.moviedbcompose.model.MovieDetailResult
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieTask
) : MovieViewModel() {

    var movieDetailResponse = mutableStateOf(MovieDetailResult())
        private set

    var status = mutableStateOf<NetworkResult<Any>?>(null)
        private set

    fun getMovieDetail(movieId: Int) {
        launchCatching {
            status.value = NetworkResult.Loading()
            val response = movieRepository.getMovieDetail(movieId, Constants.API_KEY)
            status.value = NetworkResult.Success(response)
            handleNetworkResponse(response)
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
package com.olvera.moviedbcompose.ui.search

import androidx.compose.runtime.mutableStateOf
import com.olvera.moviedbcompose.data.remote.MovieTask
import com.olvera.moviedbcompose.model.Movie
import com.olvera.moviedbcompose.model.MovieResult
import com.olvera.moviedbcompose.ui.MovieViewModel
import com.olvera.moviedbcompose.util.Constants.Companion.API_KEY
import com.olvera.moviedbcompose.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
private const val GRID_SPAN_COUNT = 2
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieTask
) : MovieViewModel() {


    var movieResponse = mutableStateOf<List<Movie>>(listOf())
        private set

    var status = mutableStateOf<NetworkResult<Any>?>(null)
        private set

    var uiState = mutableStateOf(SearchUiState())
        private set

    fun onSearchChange(value: String) {
        uiState.value = uiState.value.copy(searchQuery = value)

    }

    fun searchMovie(query: String) {
        launchCatching {
            status.value = NetworkResult.Loading()
            val response = movieRepository.searchMovie(API_KEY, query)
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
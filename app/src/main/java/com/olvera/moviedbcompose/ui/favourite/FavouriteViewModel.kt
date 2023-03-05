package com.olvera.moviedbcompose.ui.favourite

import androidx.compose.runtime.mutableStateOf
import com.olvera.moviedbcompose.data.remote.MovieTask
import com.olvera.moviedbcompose.model.Movie
import com.olvera.moviedbcompose.ui.MovieViewModel
import com.olvera.moviedbcompose.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
        private val movieRepository: MovieTask
) : MovieViewModel() {

    var movieResponse = mutableStateOf<List<Movie>>(listOf())
        private set

    var status = mutableStateOf<NetworkResult<Any>?>(null)
        private set

    init {
        getMovies()
    }

    private fun getMovies() {
        launchCatching {
            status.value = NetworkResult.Loading()
            val response = movieRepository.getMoviesFromRoom()
            movieResponse.value = response
            status.value = NetworkResult.Success(response)
        }
    }

}
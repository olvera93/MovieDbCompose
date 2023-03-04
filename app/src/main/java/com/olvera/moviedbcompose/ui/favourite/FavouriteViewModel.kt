package com.olvera.moviedbcompose.ui.favourite

import com.olvera.moviedbcompose.data.remote.MovieTask
import com.olvera.moviedbcompose.ui.MovieViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
        private val movieRepository: MovieTask
) : MovieViewModel() {


    fun getMoviesFromRoom() {
        launchCatching {
            movieRepository.getMoviesFromRoom()
        }
    }

}
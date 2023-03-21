package com.olvera.moviedbcompose.viewModel

import com.olvera.moviedbcompose.services.FakeMovieRepository
import com.olvera.moviedbcompose.ui.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MovieCoroutineRule()

    @Test
    fun `getMovies should return success`() = runBlocking {

        val viewModel = HomeViewModel(
            movieRepository = FakeMovieRepository()
        )

        // Case 1 - Success
        viewModel.getMovies()
        assertEquals(1, viewModel.movieResponse.value[0].movieId)


    }

}
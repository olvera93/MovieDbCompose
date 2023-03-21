package com.olvera.moviedbcompose.viewModel

import com.olvera.moviedbcompose.services.FakeMovieRepository
import com.olvera.moviedbcompose.ui.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MovieCoroutineRule()

    @Test
    fun `searchMovie should return success`() = runBlocking {

        val viewModel = SearchViewModel(
            movieRepository = FakeMovieRepository()
        )

        // Case 1 - Success
        viewModel.searchMovie("title")
        assertEquals(2, viewModel.movieResponse.value.size)
        assertEquals(1, viewModel.movieResponse.value[0].movieId)

    }

}
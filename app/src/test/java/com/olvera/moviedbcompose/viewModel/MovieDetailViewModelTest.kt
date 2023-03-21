package com.olvera.moviedbcompose.viewModel

import com.olvera.moviedbcompose.services.FakeMovieRepository
import com.olvera.moviedbcompose.ui.detail.MovieDetailViewModel
import com.olvera.moviedbcompose.util.NetworkResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MovieDetailViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MovieCoroutineRule()

    @Test
    fun `getMovieDetail should return success and error`() = runBlocking {

        val viewModel = MovieDetailViewModel(
            movieRepository = FakeMovieRepository()
        )

        // Case 1 - Success
        viewModel.getMovieDetail(1)
        assertEquals(1, viewModel.movieDetailResponse.value.movieDetail?.id)
        assertEquals("poster_path", viewModel.movieDetailResponse.value.movieDetail?.poster_path)
        assertEquals("title", viewModel.movieDetailResponse.value.movieDetail?.title)
        assertEquals("overview", viewModel.movieDetailResponse.value.movieDetail?.overview)
        assert(viewModel.status.value is NetworkResult.Success)

    }

    @Test
    fun `getMovieVideos should return success`() = runBlocking {

        val viewModel = MovieDetailViewModel(
            movieRepository = FakeMovieRepository()
        )

        // Case 1 - Success
        viewModel.getMovieVideo(1)
        assertEquals("1", viewModel.movieVideoResponse.value[0].id)
        assertEquals("key", viewModel.movieVideoResponse.value[0].key)
        assertEquals("name", viewModel.movieVideoResponse.value[0].name)
        assert(viewModel.status.value is NetworkResult.Success)

    }
}
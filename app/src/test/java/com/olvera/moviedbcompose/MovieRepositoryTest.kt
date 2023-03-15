package com.olvera.moviedbcompose

import com.olvera.moviedbcompose.data.remote.MovieApi
import com.olvera.moviedbcompose.data.remote.MovieRepository
import com.olvera.moviedbcompose.data.room.MovieDao
import com.olvera.moviedbcompose.services.FakeMovieDao
import com.olvera.moviedbcompose.services.FakeMovieService
import com.olvera.moviedbcompose.util.NetworkResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    private val dispatcher = TestCoroutineDispatcher()
    private lateinit var movieDao: MovieDao
    private lateinit var movieService: MovieApi

    @Before
    fun setup() {
        dispatcher.cleanupTestCoroutines()
        movieDao = FakeMovieDao()
        movieService = FakeMovieService()
    }


    @Test
    fun `getMovieDetail should return success and error`() = runBlocking {
        val movieRepository = MovieRepository(movieService, movieDao, dispatcher)

        val apiResponseStatus = movieRepository.getMovieDetail(1, "api_key")
        assert(apiResponseStatus is NetworkResult.Success)
        assertEquals((apiResponseStatus as NetworkResult.Success).data.id, 1)
    }


}
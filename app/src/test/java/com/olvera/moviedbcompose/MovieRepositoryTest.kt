package com.olvera.moviedbcompose

import com.olvera.moviedbcompose.data.remote.MovieApi
import com.olvera.moviedbcompose.data.remote.MovieRepository
import com.olvera.moviedbcompose.data.room.MovieDao
import com.olvera.moviedbcompose.model.Movie
import com.olvera.moviedbcompose.services.FakeMovieDao
import com.olvera.moviedbcompose.services.FakeMovieService
import com.olvera.moviedbcompose.util.NetworkResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var movieDao: MovieDao
    private lateinit var movieService: MovieApi

    @Before
    fun setup() {
        movieDao = FakeMovieDao()
        movieService = FakeMovieService()
    }

    @Test
    fun `getMovies should return success and error`() = runBlocking {
        val movieRepository = MovieRepository(movieService, movieDao, dispatcher)

        var apiResponseStatus = movieRepository.getMovies("api_key")
        assert(apiResponseStatus is NetworkResult.Success)
        assertEquals((apiResponseStatus as NetworkResult.Success).data.results.size, 2)

        // Case 2 - Error
        apiResponseStatus = movieRepository.getMovies("1")
        assert(apiResponseStatus is NetworkResult.Error)
        assertEquals((apiResponseStatus as NetworkResult.Error).message, "Movie not found")
    }

    @Test
    fun `getMovieDetail should return success and error`() = runBlocking {
        val movieRepository = MovieRepository(movieService, movieDao, dispatcher)

        var apiResponseStatus = movieRepository.getMovieDetail(1, "api_key")
        assert(apiResponseStatus is NetworkResult.Success)
        assertEquals((apiResponseStatus as NetworkResult.Success).data.id, 1)

        // Case 2 - Error
        apiResponseStatus = movieRepository.getMovieDetail(2, "api_key")
        assert(apiResponseStatus is NetworkResult.Error)
        assertEquals((apiResponseStatus as NetworkResult.Error).message, "Movie not found")
    }

    @Test
    fun `getMovieVideos should return success`() = runBlocking {
        val movieRepository = MovieRepository(movieService, movieDao, dispatcher)

        var apiResponseStatus = movieRepository.getMovieVideos(1, "api_key")
        assert(apiResponseStatus is NetworkResult.Success)
        assertEquals((apiResponseStatus as NetworkResult.Success).data.results.size, 1)

        // Case 2 - Error
        apiResponseStatus = movieRepository.getMovieVideos(2, "api_key")
        assert(apiResponseStatus is NetworkResult.Error)
        assertEquals((apiResponseStatus as NetworkResult.Error).message, "Video not found")
    }

    @Test
    fun `searchMovie should return success`() = runBlocking {
        val movieRepository = MovieRepository(movieService, movieDao, dispatcher)

        var apiResponseStatus = movieRepository.searchMovie("api_key", "movie")
        assert(apiResponseStatus is NetworkResult.Success)
        assertEquals((apiResponseStatus as NetworkResult.Success).data.results.size, 2)


        // Case 2 - Error
        apiResponseStatus = movieRepository.searchMovie("api_key", "movie2")
        assert(apiResponseStatus is NetworkResult.Error)
        assertEquals((apiResponseStatus as NetworkResult.Error).message, "Movie not found")
    }

    @Test
    fun `insertMovie in Room should return success`() = runBlocking {
        val movieRepository = MovieRepository(movieService, movieDao, dispatcher)

        assertEquals(movieRepository.addMovieToRoom(Movie(
            movieId = 1,
            poster_path = "poster_path",
            overview = "overview",
            release_date = "release_date",
            original_title = "original_title",
            original_language = "original_language",
            title = "title",
            backdrop_path = "backdrop_path",
            popularity = 1.0,
            vote_count = 1,
            video = false,
            vote_average = 1.0
        )), Unit)

        assertEquals(movieRepository.getMoviesFromRoom().size, 1)
    }

    @Test
    fun `getMoviesFromRoom should return success`() = runBlocking {
        val movieRepository = MovieRepository(movieService, movieDao, dispatcher)

        assertEquals(movieRepository.getMoviesFromRoom().size, 1)
    }

    @Test
    fun `getMovieByIdFromRoom should return success`() = runBlocking {
        val movieRepository = MovieRepository(movieService, movieDao, dispatcher)

        assertEquals(movieRepository.getMovieById(1).movieId, 1)
    }

    @Test
    fun `deleteMovieFromRoom should return success`() = runBlocking {
        val movieRepository = MovieRepository(movieService, movieDao, dispatcher)

        val movie = Movie(
            movieId = 1,
            poster_path = "poster_path",
            overview = "overview",
            release_date = "release_date",
            original_title = "original_title",
            original_language = "original_language",
            title = "title",
            backdrop_path = "backdrop_path",
            popularity = 1.0,
            vote_count = 1,
            video = false,
            vote_average = 1.0
        )

        assertEquals(movieRepository.deleteMovieToRoom(movie), Unit)

        assertEquals(movieRepository.getMoviesFromRoom().isEmpty(), false)

    }

}
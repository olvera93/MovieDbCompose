package com.olvera.moviedbcompose.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.olvera.moviedbcompose.R
import com.olvera.moviedbcompose.composable.BackNavigationIcon
import com.olvera.moviedbcompose.model.MovieDetail
import com.olvera.moviedbcompose.model.MovieGenre
import com.olvera.moviedbcompose.util.Constants
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.olvera.moviedbcompose.model.MovieVideo
import com.olvera.moviedbcompose.util.Constants.Companion.buildYouTubeThumbnailURL
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit
) {

    val movieDetail = viewModel.movieDetailResponse.value.movieDetail
    val movieVideo = viewModel.movieVideoResponse.value
    LaunchedEffect(Unit) {
        viewModel.getMovieDetail(movieId)
        viewModel.getMovieVideo(movieId)
    }

    Scaffold(topBar = {
        MovieDetailScreenTopBar(onNavigationIconClick)
    }) {

        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 64.dp)
        ) {

            Column {

                movieDetail?.let { movie ->
                    MovieDetailImage(movie)
                    movie.genres?.let { genres ->
                        MovieDetailGenres(genres)
                    }
                    MovieDetailInfo(movie)

                    Column(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Rate", style = MaterialTheme.typography.body1)
                        RateStars(
                            voteAverage = movie.vote_average,
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }

                    Divider(
                        color = Color(0xFFE0E0E0),
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

                    MovieDetailReview(movie)

                }

                HorizontalPager(
                    count = movieVideo.size
                ) { page ->

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Card(
                            modifier = Modifier
                                .graphicsLayer {
                                    // Calculate the absolute offset for the current page from the
                                    // scroll position. We use the absolute value which allows us to mirror
                                    // any effects for both directions
                                    val pageOffset =
                                        calculateCurrentOffsetForPage(page).absoluteValue

                                    // We animate the scaleX + scaleY, between 85% and 100%
                                    lerp(
                                        start = 0.85f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    ).also { scale ->
                                        scaleX = scale
                                        scaleY = scale
                                    }

                                    // We animate the alpha, between 50% and 100%
                                    alpha = lerp(
                                        start = 0.5f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )
                                }

                        ) {
                            val movie = movieVideo[page]
                            MovieVideoGridItem(movie)
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MovieVideoGridItem(
    movie: MovieVideo,

    ) {
    Box(
        modifier = Modifier
            .clickable {
                //onMovieClicked(movie.id.toInt())
            }
    ) {
        Column {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(buildYouTubeThumbnailURL(movie.key))
                    .crossfade(true)
                    .build(),
                contentDescription = movie.name
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieDetailGenres(genres: List<MovieGenre>) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        genres.forEach { genre ->
            Chip(onClick = { }, modifier = Modifier.padding(4.dp), enabled = true) {
                Text(text = genre.name)

            }
        }
    }
}

@Composable
fun MovieDetailInfo(movie: MovieDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Release Date", style = MaterialTheme.typography.body1)
            movie.release_date?.let {
                Text(text = it, style = MaterialTheme.typography.body1)
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Duration", style = MaterialTheme.typography.body1)
            movie.runtime?.let { Text(text = "$it min", style = MaterialTheme.typography.body1) }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Vote", style = MaterialTheme.typography.body1)
            Text(text = movie.vote_count.toString(), style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun MovieDetailReview(movie: MovieDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        movie.overview?.let { Text(text = it, style = MaterialTheme.typography.body1) }
    }
}

@Composable
private fun RateStars(voteAverage: Double, modifier: Modifier) {
    Row(modifier.padding(start = 4.dp)) {
        val maxVote = 10
        val starCount = 5
        repeat(starCount) { starIndex ->
            val voteStarCount = voteAverage / (maxVote / starCount)
            val asset = when {
                voteStarCount >= starIndex + 1 -> Icons.Filled.Star
                voteStarCount in starIndex.toDouble()..(starIndex + 1).toDouble() -> Icons.Filled.StarHalf
                else -> Icons.Filled.StarOutline
            }

            val tint = Color(0xFFFAD40C)
            Icon(imageVector = asset, contentDescription = null, tint = tint)
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Composable
fun MovieDetailImage(movie: MovieDetail) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .blur(
                    radiusX = 2.dp,
                    radiusY = 2.dp
                ),
            model = ImageRequest.Builder(LocalContext.current)
                .data(Constants.IMAGE_BASE_URL + Constants.IMAGE_W500 + movie.backdrop_path)
                .crossfade(true).build(),
            contentDescription = movie.title,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 100.dp),
            contentAlignment = Alignment.Center
        ) {

            Card(
                shape = MaterialTheme.shapes.medium,
                elevation = 8.dp,
                border = BorderStroke(0.5.dp, MaterialTheme.colors.onSurface)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Constants.IMAGE_BASE_URL + Constants.IMAGE_W500 + movie.poster_path)
                        .crossfade(true).build(),
                    contentDescription = movie.title
                )
            }
            movie.title?.let { Text(text = it, style = MaterialTheme.typography.h5) }
        }
    }
}

@Composable
fun MovieDetailScreenTopBar(onClick: () -> Unit) {

    TopAppBar(modifier = Modifier.padding(0.dp),
        title = { Text(text = stringResource(id = R.string.topBar_detail)) },
        navigationIcon = { BackNavigationIcon(onClick) }

    )
}
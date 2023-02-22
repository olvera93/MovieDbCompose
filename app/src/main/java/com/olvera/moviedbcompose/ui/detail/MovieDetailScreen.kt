package com.olvera.moviedbcompose.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.core.net.toUri
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.olvera.moviedbcompose.model.MovieVideo
import com.olvera.moviedbcompose.util.Constants.Companion.buildYouTubeThumbnailURL
import com.olvera.moviedbcompose.util.Constants.Companion.buildYoutubeURL
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

    val context = LocalContext.current

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
                .padding(bottom = 56.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Column {

                movieDetail?.let { movie ->
                    MovieDetailImage(movie)
                    movie.title?.let {
                        Text(
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .padding(8.dp),
                            text = it,
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.h5
                        )
                    }
                    movie.genres?.let { genres ->
                        MovieDetailGenres(genres)
                    }
                    MovieDetailInfo(movie)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = CenterHorizontally) {
                            Text(
                                text = stringResource(id = R.string.rate_movie),
                                style = MaterialTheme.typography.body1
                            )

                            RateStars(
                                voteAverage = movie.vote_average,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                        }

                        Column(horizontalAlignment = CenterHorizontally) {
                            var isFavorite by remember { mutableStateOf(false) }
                            Text(
                                text = stringResource(id = R.string.add_to_favourite),
                                style = MaterialTheme.typography.body1
                            )
                            IconToggleButton(
                                checked = isFavorite,
                                onCheckedChange = { isFavorite = !isFavorite }) {
                                Icon(
                                    tint = Color.Red,
                                    imageVector = if (isFavorite) {
                                        Icons.Filled.Favorite
                                    } else {
                                        Icons.Default.FavoriteBorder
                                    },
                                    contentDescription = null
                                )
                            }
                        }
                    }

                    Divider(
                        color = Color(0xFFE0E0E0),
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

                    MovieDetailReview(movie)

                    Divider(
                        color = Color(0xFFE0E0E0),
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
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
                                    val pageOffset =
                                        calculateCurrentOffsetForPage(page).absoluteValue
                                    lerp(
                                        start = 0.85f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )
                                    alpha = lerp(
                                        start = 0.5f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )
                                }

                        ) {
                            val movie = movieVideo[page]
                            MovieVideoGridItem(movie, onMovieClicked = {
                                val intent = Intent(Intent.ACTION_VIEW, it)
                                context.startActivity(intent)
                            })
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
    onMovieClicked: (Uri) -> Unit
) {
    Box(
        modifier = Modifier
            .clickable {
                onMovieClicked(buildYoutubeURL(movie.key).toUri())
            }
    ) {

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .width(200.dp)
                .height(300.dp)
            ,
            model = ImageRequest.Builder(LocalContext.current)
                .data(buildYouTubeThumbnailURL(movie.key))
                .crossfade(true)
                .build(),
            contentDescription = movie.name
        )

        Icon(
            imageVector = Icons.Outlined.PlayCircleOutline,
            contentDescription = "Play",
            tint = Color.White,
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.Center)
        )
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
        Column(horizontalAlignment = CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.release_date),
                style = MaterialTheme.typography.body1
            )
            movie.release_date?.let {
                Text(text = it, style = MaterialTheme.typography.body1)
            }
        }

        Column(horizontalAlignment = CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.duration_movie),
                style = MaterialTheme.typography.body1
            )
            movie.runtime?.let { Text(text = "$it min", style = MaterialTheme.typography.body1) }
        }

        Column(horizontalAlignment = CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.vote_movie),
                style = MaterialTheme.typography.body1
            )
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
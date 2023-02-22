package com.olvera.moviedbcompose.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import java.lang.Math.ceil
import java.lang.Math.floor


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit
) {

    val movieDetail = viewModel.movieDetailResponse.value.movieDetail
    LaunchedEffect(Unit) { viewModel.getMovieDetail(movieId) }

    Scaffold(topBar = {
        MovieDetailScreenTopBar(onNavigationIconClick)
    }) {

        Surface(color = MaterialTheme.colors.background) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                movieDetail?.let { movie ->
                    MovieDetailImage(movie)
                    movie.genres?.let { genres ->
                        MovieDetailGenres(genres)
                    }

                    // add release date and runtime here
                    MovieDetailInfo(movie)
                    RateStars(
                        voteAverage = movie.vote_average,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                    )

                    MovieDetailReview(movie)
                }
            }
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
        Column {
            Text(text = "Release Date", style = MaterialTheme.typography.body1)
            movie.release_date?.let { Text(text = it, style = MaterialTheme.typography.body1) }
        }

        Column {
            Text(text = "Duration", style = MaterialTheme.typography.body1)
            movie.runtime?.let { Text(text = it.toString(), style = MaterialTheme.typography.body1) }
        }

        Column {
            Text(text = "Vote", style = MaterialTheme.typography.body1)
            movie.vote_count?.let { Text(text = it.toString(), style = MaterialTheme.typography.body1) }
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

        movie.title?.let { Text(text = it, style = MaterialTheme.typography.h5) }
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
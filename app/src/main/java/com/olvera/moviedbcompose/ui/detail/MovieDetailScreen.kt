package com.olvera.moviedbcompose.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
                    // Mostrar generos en chips
                    movie.genres?.let { genres ->
                        MovieDetailGenres(genres)
                    }
                    MovieDetailInfo(movie)
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
                .padding(16.dp)
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
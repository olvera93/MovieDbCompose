package com.olvera.moviedbcompose.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.olvera.moviedbcompose.R
import com.olvera.moviedbcompose.composable.BackNavigationIcon
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
    }
    ) {

        Text(text = movieDetail?.title ?: "Loading")

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Constants.IMAGE_BASE_URL + Constants.IMAGE_W500 + movieDetail?.backdrop_path)
                    .crossfade(true)
                    .build(),
                contentDescription = movieDetail?.title
            )
        }
    }

}

@Composable
fun MovieDetailScreenTopBar(onClick: () -> Unit) {

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.topBar_detail))},
        navigationIcon = { BackNavigationIcon(onClick) }

    )
}
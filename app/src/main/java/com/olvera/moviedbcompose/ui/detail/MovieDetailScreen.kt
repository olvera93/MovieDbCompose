package com.olvera.moviedbcompose.ui.detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {

    val movieDetail = viewModel.movieDetailResponse.value.movieDetail
    LaunchedEffect(Unit) { viewModel.getMovieDetail(movieId) }

    Text(text = movieDetail?.title  ?: "Loading")

}
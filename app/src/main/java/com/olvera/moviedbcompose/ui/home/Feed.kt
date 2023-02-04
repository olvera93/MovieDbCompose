package com.olvera.moviedbcompose.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.olvera.moviedbcompose.model.Movie

private const val GRID_SPAN_COUNT = 3

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Feed(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val moviePopular = viewModel.movieResponse.value

    LazyVerticalGrid(columns = GridCells.Fixed(GRID_SPAN_COUNT)) {
        items(moviePopular) { movie ->
            MovieGridItem(movie)
        }
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MovieGridItem(
    movie: Movie
) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 6.dp,
        shape = RoundedCornerShape(4.dp)
    ) {

        Column {
            Text(text = movie.title)

        }

    }
}
package com.olvera.moviedbcompose.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.olvera.moviedbcompose.composable.SearchField

private const val GRID_SPAN_COUNT = 2

@Composable
fun Search(
    viewModel: SearchViewModel = hiltViewModel()
) {

    val movies = viewModel.movieResponse.value
    val uiState by viewModel.uiState

    Column {
        SearchField(
            value = uiState.searchQuery,
            onNewValue = {
                viewModel.onSearchChange(it)
                viewModel.searchMovie(it)
            },
            modifier = Modifier.padding(10.dp)
        )

        Spacer(modifier = Modifier.padding(10.dp))
        LazyVerticalGrid(
            modifier = Modifier.padding(top = 16.dp),
            columns = GridCells.Fixed(GRID_SPAN_COUNT),
            content = {
                items(movies) { movie ->
                    movie.title?.let { Text(text = it) }
                }
            }

        )
    }



}
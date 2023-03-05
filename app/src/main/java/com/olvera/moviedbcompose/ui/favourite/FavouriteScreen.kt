package com.olvera.moviedbcompose.ui.favourite

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.olvera.moviedbcompose.R
import com.olvera.moviedbcompose.composable.BackNavigationIcon
import com.olvera.moviedbcompose.model.Movie
import com.olvera.moviedbcompose.util.Constants


private const val GRID_SPAN_COUNT = 2

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavouriteScreen(
        onNavigationIconClick: () -> Unit,
        viewModel: FavouriteViewModel = hiltViewModel(),
        onMovieClicked: (Int) -> Unit
) {

    val movie = viewModel.movieResponse.value



    Scaffold(
            topBar = { MovieFavouriteScreenTopBar(onClick = onNavigationIconClick) }

    ) {

        LazyVerticalGrid(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 64.dp),
                columns = GridCells.Fixed(GRID_SPAN_COUNT),
                content = {
                    items(movie) { movie ->

                        Box {

                            MovieFavouriteGridItem(movie, onMovieClicked = onMovieClicked)
                        }
                    }
                }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieFavouriteGridItem(movie: Movie, onMovieClicked: (Int) -> Unit) {

    Card(
            modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .offset(y = 12.dp),
            shape = RoundedCornerShape(size = 8.dp),
            elevation = 8.dp,
            onClick = { onMovieClicked(movie.movieId) }

    ) {

        Box {

            AsyncImage(
                    modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                    model = ImageRequest.Builder(LocalContext.current)
                            .data(Constants.IMAGE_BASE_URL + Constants.IMAGE_W500 + movie.poster_path)
                            .crossfade(true)
                            .build(),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.ic_image_not_supported),
                    placeholder = painterResource(id = R.drawable.ic_image)
            )
        }
    }
}

@Composable
fun MovieFavouriteScreenTopBar(onClick: () -> Unit) {

    TopAppBar(modifier = Modifier.padding(0.dp),
            title = { Text(text = stringResource(id = R.string.topBar_favourite)) },
            navigationIcon = { BackNavigationIcon(onClick) }

    )
}
package com.olvera.moviedbcompose.ui.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.olvera.moviedbcompose.composable.SearchField
import com.olvera.moviedbcompose.model.Movie
import com.olvera.moviedbcompose.util.Constants.Companion.IMAGE_BASE_URL
import com.olvera.moviedbcompose.util.Constants.Companion.IMAGE_W500
import com.olvera.moviedbcompose.R
import com.olvera.moviedbcompose.util.rateColors


private const val GRID_SPAN_COUNT = 2

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class, ExperimentalCoilApi::class)
@Composable
fun Search(
    viewModel: SearchViewModel = hiltViewModel(),
    onMovieClicked: (Int) -> Unit,
) {

    val movies = viewModel.movieResponse.value
    val uiState by viewModel.uiState

    Column( modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){

        SearchField(
            value = uiState.searchQuery,
            onNewValue = {
                viewModel.onSearchChange(it)
                viewModel.searchMovie(it)
            },
            modifier = Modifier
                .padding(10.dp)

        )
        Spacer(modifier = Modifier.padding(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_SPAN_COUNT),
            content = {
                items(movies) { movie ->
                    Box {
                        MovieRate(
                            movie, modifier = Modifier
                                .align(Alignment.TopCenter)
                                .zIndex(2f)
                        )
                        MovieSearchGridItem(movie, onMovieClicked)
                    }
                }
            }
        )
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MovieSearchGridItem(
    movie: Movie,
    onMovieClicked: (Int) -> Unit
) {
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
                    .data(IMAGE_BASE_URL + IMAGE_W500 + movie.poster_path)
                    .crossfade(true)
                    .build(),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_image_not_supported),
                placeholder = painterResource(id = R.drawable.ic_image)
            )

            MovieInfo(
                movie,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color(0x97000000))
            )
        }
    }
}

@Composable
private fun MovieRate(movie: Movie, modifier: Modifier) {
    val shape = RoundedCornerShape(percent = 50)
    Surface(
        shape = shape,
        elevation = 12.dp,
        modifier = modifier.padding(top = 8.dp, start = 8.dp)
    ) {
        Text(
            text = movie.vote_average.toString(),
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            modifier = Modifier
                .background(Brush.horizontalGradient(Color.rateColors(movieRate = movie.vote_average)))
                .padding(horizontal = 10.dp)
        )
    }
}

@Composable
private fun MovieInfo(movie: Movie, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(horizontal = 6.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        movie.title?.let { MovieTitle(name = it) }
    }
}

@Composable
private fun MovieTitle(name: String) = Text(
    text = name,
    style = MaterialTheme.typography.subtitle1.copy(
        color = Color.White,

        ),
    maxLines = 1,
    overflow = TextOverflow.Ellipsis
)
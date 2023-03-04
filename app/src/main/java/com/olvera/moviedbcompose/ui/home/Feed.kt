package com.olvera.moviedbcompose.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.olvera.moviedbcompose.model.Movie
import kotlin.math.absoluteValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import com.olvera.moviedbcompose.R
import com.olvera.moviedbcompose.composable.LoadingWheel
import com.olvera.moviedbcompose.util.Constants.Companion.IMAGE_BASE_URL
import com.olvera.moviedbcompose.util.Constants.Companion.IMAGE_W500
import com.olvera.moviedbcompose.util.NetworkResult
import com.olvera.moviedbcompose.util.rateColors


private const val GRID_SPAN_COUNT = 2

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Feed(
    onMovieClicked: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    onFavoriteClicked: () -> Unit
) {
    val moviePopular = viewModel.movieResponse.value
    val status = viewModel.status.value


    Scaffold(
        topBar = {
            Surface(modifier = Modifier.fillMaxWidth(), elevation = 16.dp) {
                Column(
                    Modifier
                        .background(MaterialTheme.colors.surface)
                        .padding(bottom = 2.dp)
                ) {
                    MovieScreenTopBar(onFavoriteClicked = onFavoriteClicked)
                }
            }
        }
    ) {
        Column {
            HorizontalPager(
                count = moviePopular.size
            ) { page ->

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 56.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Card(
                        modifier = Modifier
                            .graphicsLayer {
                                // Calculate the absolute offset for the current page from the
                                // scroll position. We use the absolute value which allows us to mirror
                                // any effects for both directions
                                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

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
                        val movie = moviePopular[page]
                        MoviePopularGridItem(movie, onMovieClicked)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 240.dp, bottom = 60.dp)
        ) {
            Text(
                text = if (status is NetworkResult.Loading) "Loading..." else "Upcoming Movies",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 275.dp, bottom = 60.dp)
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(GRID_SPAN_COUNT),
                content = {
                    items(moviePopular) { movie ->

                        Box {
                            MovieRate(
                                movie, modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .zIndex(2f)
                            )
                            MovieUpcomingGridItem(movie, onMovieClicked)
                        }
                    }
                }
            )
        }
    }

    if (status is NetworkResult.Loading) {
        LoadingWheel()
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MoviePopularGridItem(
    movie: Movie,
    onMovieClicked: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .clickable {
                onMovieClicked(movie.movieId)
            }
    ) {
        Column {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(IMAGE_BASE_URL + IMAGE_W500 + movie.backdrop_path)
                    .crossfade(true)
                    .build(),
                contentDescription = movie.title
            )
        }
        MovieInfo(
            movie = movie, modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color(0x97000000))
        )
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MovieUpcomingGridItem(
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

@Composable
fun MovieScreenTopBar(
    onFavoriteClicked: () -> Unit
) {

    // TopBar with Favourite Icon and Title
    TopAppBar(
        title = { Text(text = "Título") },
        backgroundColor = Color.Cyan,
        navigationIcon = {
            IconButton(onClick = {
                onFavoriteClicked()
            }
            ) {
                Icon(Icons.Filled.ArrowBack, null)
            }
        }
    )

}

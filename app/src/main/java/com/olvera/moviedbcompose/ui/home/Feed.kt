package com.olvera.moviedbcompose.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
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
import com.olvera.moviedbcompose.util.Constants
import kotlin.math.absoluteValue

private const val GRID_SPAN_COUNT = 2

@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Feed(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val moviePopular = viewModel.movieResponse.value

    if (moviePopular.isNotEmpty()) {
        HorizontalPager(
            count = moviePopular.size
        ) { page ->

            Box(
                modifier = Modifier.fillMaxSize(),
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
                    MoviePopularGridItem(movie)


                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 250.dp, start = 8.dp, end = 8.dp, bottom = 48.dp)
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(GRID_SPAN_COUNT),
                content = {
                    items(moviePopular) { movie ->
                        MovieUpcomingGridItem(movie)
                    }
                }
            )
        }

    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MoviePopularGridItem(
    movie: Movie
) {
    Column(horizontalAlignment = Alignment.End) {

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(Constants.IMAGE_BASE_URL + Constants.IMAGE_W500 + movie.backdrop_path)
                .crossfade(true)
                .build(),
            contentDescription = movie.title
        )

    }
    movie.title?.let { Text(text = it, textAlign = TextAlign.End) }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MovieUpcomingGridItem(
    movie: Movie
) {
    Surface(
        modifier = Modifier
            .padding(8.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(Constants.IMAGE_BASE_URL + Constants.IMAGE_W500 + movie.poster_path)
                .crossfade(true)
                .build(),
            contentDescription = movie.title
        )
        Box {
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


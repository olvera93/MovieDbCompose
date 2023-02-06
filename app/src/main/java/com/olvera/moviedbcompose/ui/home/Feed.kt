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
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.olvera.moviedbcompose.util.Constants.Companion.IMAGE_BASE_URL
import com.olvera.moviedbcompose.util.Constants.Companion.IMAGE_W500
import com.olvera.moviedbcompose.util.rateColors


private const val GRID_SPAN_COUNT = 2

@ExperimentalPagerApi
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
                .padding(top = 240.dp, bottom = 60.dp)
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
                            MovieUpcomingGridItem(movie)
                        }
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
    Box {
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
    movie: Movie
) {

    val painter = rememberAsyncImagePainter(
        model = IMAGE_BASE_URL + IMAGE_W500 + movie.poster_path,
        error = rememberVectorPainter(Icons.Filled.Add),
        placeholder = rememberVectorPainter(Icons.Default.MoreVert)
    )

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .offset(y = 12.dp),
        shape = RoundedCornerShape(size = 8.dp),
        elevation = 8.dp,

        ) {

        Box {
            Image(
                painter = painter,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
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


package com.olvera.moviedbcompose.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

private const val GRID_SPAN_COUNT = 3

@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Feed(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val moviePopular = viewModel.movieResponse.value

    // Mostrar el carrusel de peliculas populares pero solo se muestre una sola vez
    if (moviePopular.isNotEmpty()) {
        HorizontalPager(
            count = moviePopular.size
        ) { page ->
            Card(
                Modifier
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
                MovieGridItem(movie)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MovieGridItem(
    movie: Movie
) {

    // Card content


    Column {

        AsyncImage(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(Constants.IMAGE_BASE_URL + Constants.IMAGE_W500 + movie.poster_path)
                .crossfade(true)
                .build(),
            contentDescription = movie.title
        )
        movie.title?.let { Text(text = it) }

    }


}


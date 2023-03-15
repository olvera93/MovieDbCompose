package com.olvera.moviedbcompose.nav

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.olvera.moviedbcompose.nav.ItemMenu.*
import com.olvera.moviedbcompose.ui.detail.MovieDetailScreen
import com.olvera.moviedbcompose.ui.favourite.FavouriteScreen
import com.olvera.moviedbcompose.ui.home.Feed
import com.olvera.moviedbcompose.ui.search.Search
import com.olvera.moviedbcompose.util.Constants.Companion.ARG_MOVIE_ID
import com.olvera.moviedbcompose.util.Constants.Companion.MOVIE_DETAIL
import com.olvera.moviedbcompose.util.Constants.Companion.MOVIE_FAVOURITE

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun NavigationHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Home.route) {

        composable(Home.route) {
            Feed(onMovieClicked = {
                navController.navigate("movie/${it}/detail")
            },
                    onFavoriteClicked = {
                        navController.navigate(MOVIE_FAVOURITE)
                    }
            )


        }

        composable(Search.route) {
            Search(
                onMovieClicked = {
                    navController.navigate("movie/${it}/detail")
                }
            )
        }

        composable(MOVIE_FAVOURITE) {
            FavouriteScreen(
                    onNavigationIconClick = { navController.navigate(Home.route) },
                    onMovieClicked = {
                        navController.navigate("movie/${it}/detail")
                    }
            )
        }

        composable(
                route = MOVIE_DETAIL,
                arguments = listOf(navArgument(ARG_MOVIE_ID) {
                    defaultValue = 0
                })
        ) {
            val movieId = it.arguments?.getInt(ARG_MOVIE_ID)
            MovieDetailScreen(
                    movieId = movieId ?: 0,
                    onNavigationIconClick = { navController.navigate(Home.route) })
        }
    }

}
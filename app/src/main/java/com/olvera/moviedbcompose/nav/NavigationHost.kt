package com.olvera.moviedbcompose.nav

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.olvera.moviedbcompose.model.MovieDetail
import com.olvera.moviedbcompose.nav.ItemMenu.*
import com.olvera.moviedbcompose.ui.detail.MovieDetailScreen
import com.olvera.moviedbcompose.ui.home.Feed
import com.olvera.moviedbcompose.ui.home.Search
import com.olvera.moviedbcompose.util.Constants.Companion.ARG_MOVIE_ID
import com.olvera.moviedbcompose.util.Constants.Companion.MOVIE_DETAIL

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun NavigationHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Home.route) {

        composable(Home.route) {
            Feed(onMovieClicked = {
                navController.navigate("movie/${it}/detail")
            })


        }

        composable(Search.route) {
            Search()
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
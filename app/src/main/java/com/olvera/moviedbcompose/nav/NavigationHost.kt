package com.olvera.moviedbcompose.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.olvera.moviedbcompose.nav.ItemMenu.*
import com.olvera.moviedbcompose.ui.home.Feed
import com.olvera.moviedbcompose.ui.home.Search

@Composable
fun NavigationHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Home.route) {

        composable(Home.route) {
            Feed()
        }

        composable(Search.route) {
            Search()
        }
    }

}
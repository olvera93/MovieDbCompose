package com.olvera.moviedbcompose.nav

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun MovieComposeApp() {

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    val navigationItem = listOf(
        ItemMenu.Home,
        ItemMenu.Search
    )

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { InferiorNavigation(navController, navigationItem) },
    ) {
        NavigationHost(navController)
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val entree by navController.currentBackStackEntryAsState()
    return entree?.destination?.route
}


@Composable
fun InferiorNavigation(
    navController: NavHostController,
    menuItems: List<ItemMenu>
) {

    BottomAppBar {
        BottomNavigation {
            val currentRoute = currentRoute(navController = navController)
            menuItems.forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.route,
                    onClick = { navController.navigate(item.route) },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title.toString()
                        )
                    },
                    label = { Text(text = stringResource(id = item.title)) },
                    alwaysShowLabel = false
                )
            }
        }
    }
}

package com.olvera.moviedbcompose.ui.favourite

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.olvera.moviedbcompose.R
import com.olvera.moviedbcompose.composable.BackNavigationIcon

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavouriteScreen(
        onNavigationIconClick: () -> Unit,
        viewModel: FavouriteViewModel = hiltViewModel()
) {
    Scaffold(
            topBar = { MovieFavouriteScreenTopBar(onClick = onNavigationIconClick) }

    ) {

        LazyColumn(content = {
            item {
                println("Favourite Screen")
                println(viewModel.getMoviesFromRoom())
            }
        }

        )

    }
}



@Composable
fun MovieFavouriteScreenTopBar(onClick: () -> Unit) {

    TopAppBar(modifier = Modifier.padding(0.dp),
            title = { Text(text = stringResource(id = R.string.topBar_favourite)) },
            navigationIcon = { BackNavigationIcon(onClick) }

    )
}
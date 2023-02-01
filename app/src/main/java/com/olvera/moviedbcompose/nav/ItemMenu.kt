package com.olvera.moviedbcompose.nav

import androidx.annotation.StringRes
import com.olvera.moviedbcompose.R

sealed class ItemMenu(
    val icon: Int,
    @StringRes val title: Int,
    val route: String
) {

    object Home: ItemMenu(R.drawable.ic_home, R.string.home_feed, "home")
    object Search: ItemMenu(R.drawable.ic_search, R.string.home_search, "search")


}

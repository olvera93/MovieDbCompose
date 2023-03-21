package com.olvera.moviedbcompose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import coil.annotation.ExperimentalCoilApi
import com.olvera.moviedbcompose.nav.MovieComposeApp
import com.olvera.moviedbcompose.ui.theme.MovieDbComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class,
        ExperimentalCoilApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieDbComposeTheme(
                darkTheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    resources.configuration.isNightModeActive
                } else {
                    false
                }
            ) {
                MovieComposeApp()
            }
        }
    }
}

package com.olvera.moviedbcompose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

private val colorRange = 0..256

fun Color.Companion.randomColor() = Color(colorRange.random(), colorRange.random(), colorRange.random())

@Composable
fun Color.Companion.rateColors(movieRate: Double): List<Color> = remember(movieRate) {
    when {
        movieRate <= 4.5 -> listOf(Color(0xffe32d20), Color(0xff9c180e))
        movieRate < 7 -> listOf(Color(0xffe36922), Color(0xff963d09))
        movieRate < 8.5 -> listOf(Color(0xff87bf32), Color(0xff578216))
        else -> listOf(Color(0xff34c937), Color(0xff0d750f))
    }
}
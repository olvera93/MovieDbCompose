package com.olvera.moviedbcompose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olvera.moviedbcompose.composable.snackbar.SnackbarManager
import com.olvera.moviedbcompose.composable.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class MovieViewModel: ViewModel() {

    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
            },
            block = block
        )

}
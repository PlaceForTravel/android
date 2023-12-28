package com.easyhz.placeapp.ui.state


import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberApplicationState(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember { ApplicationState(snackBarHostState, coroutineScope) }

@Stable
class ApplicationState(
    private val snackBarHostState: SnackbarHostState,
    private val scope: CoroutineScope,
) {
    val snackBarState
        get() = snackBarHostState

    fun showSnackBar(message: String) {
        scope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }
}
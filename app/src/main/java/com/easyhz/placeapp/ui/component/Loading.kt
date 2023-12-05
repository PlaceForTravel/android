package com.easyhz.placeapp.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@Composable
fun CircularLoading(
    scope: BoxScope
) {
    scope.apply {
        Box(modifier = Modifier.align(Alignment.Center).zIndex(10F)) {
            CircularProgressIndicator(
                color = PlaceAppTheme.colorScheme.primary
            )
        }
    }
}
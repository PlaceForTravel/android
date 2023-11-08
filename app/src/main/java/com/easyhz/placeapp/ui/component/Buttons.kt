package com.easyhz.placeapp.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.easyhz.placeapp.ui.theme.PlaceAppTheme


@Composable
fun SimpleIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String = "Icon",
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun SimpleIconButtonPreview() {
    PlaceAppTheme {
        SimpleIconButton(
            icon = Icons.Outlined.Place,
            contentDescription = "Place",
        ) {
            
        }
    }
}
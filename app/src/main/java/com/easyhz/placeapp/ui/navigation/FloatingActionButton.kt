package com.easyhz.placeapp.ui.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonDefaults.extendedFabShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.placeapp.R
import com.easyhz.placeapp.constants.ContentCardIcons
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@Composable
fun MainFloatingActionButton(
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = onClick ,
        containerColor = PlaceAppTheme.colorScheme.subBackground,
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
        modifier = Modifier
            .border(
                width = 2.dp,
                color = PlaceAppTheme.colorScheme.primary,
                shape = extendedFabShape
            )
            .height(40.dp)
    ) {
        Icon(imageVector = ContentCardIcons.ADD.icon, contentDescription = stringResource(id = ContentCardIcons.ADD.label))
        Text(stringResource(id = R.string.post_add), modifier = Modifier.padding(start = 10.dp))
    }
}

@Preview
@Composable
private fun MainFloatingActionButtonPreview() {
    PlaceAppTheme {
        MainFloatingActionButton(onClick = {  })
    }
}
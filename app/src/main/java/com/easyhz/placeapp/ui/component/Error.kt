package com.easyhz.placeapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.easyhz.placeapp.R
import com.easyhz.placeapp.constants.ContentCardIcons
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape

@Composable
fun NetworkError(
    scope: BoxScope,
    onClick: () -> Unit
) {
    ErrorTemplate(
        scope = scope,
        icon = ContentCardIcons.ERROR.icon,
        description = stringResource(id = ContentCardIcons.ERROR.label),
        mainText = stringResource(id = R.string.network_error_message),
        onClick = onClick
    )
}

@Composable
private fun ErrorTemplate(
    scope: BoxScope,
    icon: ImageVector,
    description: String,
    mainText: String,
    onClick: () -> Unit
) {
    scope.apply {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .zIndex(9F)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = description,
                    modifier = Modifier.size(50.dp)
                )
                SpaceDivider(padding = 10)
                Text(
                    text = mainText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = stringResource(id = R.string.retry_error_message),
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = PlaceAppTheme.colorScheme.secondary, contentColor = PlaceAppTheme.colorScheme.subBackground),
                    shape = roundShape,
                    elevation = null,
                    onClick = onClick,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ContentCardIcons.REFRESH.icon,
                            contentDescription = stringResource(id = ContentCardIcons.REFRESH.label)
                        )
                        Text(
                            text = stringResource(id = R.string.retry),
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun NetworkErrorPreview() {
    Box {
        NetworkError(this) {

        }
    }
}
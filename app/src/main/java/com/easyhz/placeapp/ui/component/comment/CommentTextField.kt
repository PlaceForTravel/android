package com.easyhz.placeapp.ui.component.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.placeapp.R
import com.easyhz.placeapp.constants.ContentCardIcons
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape

const val MAX_LINES = 3
@Composable
fun CommentTextField(
    modifier: Modifier = Modifier,
    value : String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    onSendClick: () -> Unit
) {
    Box(
        Modifier.background(PlaceAppTheme.colorScheme.mainBackground)
    ) {
        TextField(
            modifier = modifier
                .border(
                    width = 3.dp,
                    color = PlaceAppTheme.colorScheme.primary,
                    shape = roundShape
                ),
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            trailingIcon = {
                SendButton(
                    onSendClick = onSendClick,
                    enabled = value.isNotEmpty()
                )
            },
            placeholder = { Text(text = stringResource(id = R.string.post_add_comment)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = PlaceAppTheme.colorScheme.transparent,
                unfocusedContainerColor = PlaceAppTheme.colorScheme.transparent,
                disabledContainerColor = PlaceAppTheme.colorScheme.transparent,
                cursorColor = PlaceAppTheme.colorScheme.mainText,
                focusedIndicatorColor = PlaceAppTheme.colorScheme.transparent,
                unfocusedIndicatorColor = PlaceAppTheme.colorScheme.transparent,
                disabledIndicatorColor = PlaceAppTheme.colorScheme.transparent,
            ),
            maxLines = MAX_LINES
        )
    }
}

@Composable
private fun SendButton(
    onSendClick: () -> Unit,
    enabled: Boolean
) {
    val iconColor = if (enabled) PlaceAppTheme.colorScheme.mainText else PlaceAppTheme.colorScheme.unselectedIcon
    IconButton(
        onClick = { if (enabled) onSendClick() }
    ) {
        Icon(
            tint = iconColor,
            imageVector = ContentCardIcons.SEND.icon,
            contentDescription = stringResource(id = ContentCardIcons.SEND.label)
        )
    }
}
@Preview
@Composable
private fun CommentTextFieldPreview() {
    CommentTextField(
        value = "",
        onValueChange = { } ,
        enabled = true
    ) {
        
    }
}
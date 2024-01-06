package com.easyhz.placeapp.ui.component.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.easyhz.placeapp.R
import com.easyhz.placeapp.ui.component.SpaceDivider
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape

@Composable
fun LoginDialog(
    onDismissRequest: () -> Unit,
    onLoginClick: () -> Unit,
    onDoNotShowAgainClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            LoginSuggestion(
                modifier = Modifier.padding(20.dp),
                onLoginClick = onLoginClick,
                onCloseClick = onDismissRequest,
                onDoNotShowAgainClick = onDoNotShowAgainClick
            )
        }
    }
}

@Composable
fun LoginSuggestion(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onCloseClick: () -> Unit,
    onDoNotShowAgainClick: () -> Unit
) {
    val filledButtonColor = ButtonDefaults.buttonColors(containerColor = PlaceAppTheme.colorScheme.secondary, contentColor = PlaceAppTheme.colorScheme.subBackground)
    val outlinedButtonColor = ButtonDefaults.buttonColors(containerColor = PlaceAppTheme.colorScheme.transparent, contentColor = PlaceAppTheme.colorScheme.secondary)
    val textButtonColor = ButtonDefaults.buttonColors(containerColor = PlaceAppTheme.colorScheme.transparent, contentColor = PlaceAppTheme.colorScheme.secondary)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpaceDivider(padding = 10)
        Text(text = stringResource(id = R.string.user_login_suggestion))
        SpaceDivider(padding = 20)
        DialogButton(
            text = stringResource(id = R.string.user_login),
            colors = filledButtonColor
        ) {
            onLoginClick()
        }
        DialogButton(
            text = stringResource(id = R.string.user_close),
            border = ButtonDefaults.outlinedButtonBorder,
            colors = outlinedButtonColor
        ) {
            onCloseClick()
        }
        DialogButton(
            text = stringResource(id = R.string.user_do_not_show_again),
            colors = textButtonColor
        ) {
            onDoNotShowAgainClick()
        }
    }
}

@Composable
private fun DialogButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    border: BorderStroke? = null,
    shape: Shape = roundShape,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        colors = colors,
        border = border,
        shape = shape,
        elevation = null,
        onClick = onClick,
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
fun LoginSuggestionPreview() {
//    LoginSuggestion()
    LoginDialog({}, {}, {})
}


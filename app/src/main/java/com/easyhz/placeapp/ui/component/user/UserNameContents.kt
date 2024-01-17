package com.easyhz.placeapp.ui.component.user


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.PersonPinCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easyhz.placeapp.R
import com.easyhz.placeapp.ui.component.SpaceDivider
import com.easyhz.placeapp.ui.component.SpinningProgressBar
import com.easyhz.placeapp.ui.component.search.setSearchBarColors
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape
import com.easyhz.placeapp.ui.user.LoginViewModel

@Composable
fun UserNameContents(
    viewModel: LoginViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 150.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.padding(30.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login_username_main_title),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left

            )
            SpaceDivider(padding = 5)
            Text(
                text = stringResource(id = R.string.login_username_sub_title),
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraLight,
                textAlign = TextAlign.Left
            )
            SpaceDivider(padding = 10)
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = PlaceAppTheme.colorScheme.secondaryBorder,
                        shape = roundShape
                    ),
                value = viewModel.userState.user.nickname,
                onValueChange = { viewModel.setNickname(it) },
                singleLine = true,
                placeholder = {
                    Text(text = stringResource(id = R.string.login_username_placeholder))
                },
                trailingIcon = {
                    when {
                        viewModel.userState.isLoading -> {
                            SpinningProgressBar(canvasSize = 24)
                        }
                        viewModel.userState.isValid -> {
                            Icon(
                                imageVector = Icons.Outlined.Check,
                                contentDescription = stringResource(id = R.string.login_nickname_valid),
                                tint = PlaceAppTheme.colorScheme.secondary
                            )
                        }
                        else -> {
                            Icon(
                                imageVector = Icons.Outlined.PersonPinCircle,
                                contentDescription = stringResource(id = R.string.login_nickname_invalid),
                                tint = PlaceAppTheme.colorScheme.error
                            )
                        }
                    }
                },
                colors = setSearchBarColors(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = {

                })
            )
            if (!viewModel.userState.isValid && !viewModel.userState.isLoading) {
                Text(text = stringResource(id = R.string.login_nickname_invalid_text))
            }
            SpaceDivider(padding = 10)
            Button(
                onClick = { viewModel.onSuccess() },
                modifier = Modifier.fillMaxWidth(),
                shape = roundShape,
                enabled = viewModel.userState.isValid,
                colors = ButtonDefaults.buttonColors(containerColor = PlaceAppTheme.colorScheme.secondary, contentColor = PlaceAppTheme.colorScheme.subBackground)
            ) {
                Text(text = stringResource(id = R.string.login_join_success_request), fontSize = 17.sp)
            }
        }
    }
}

@Preview
@Composable
private fun NicknamePreview() {
    PlaceAppTheme {
        UserNameContents()
    }
}
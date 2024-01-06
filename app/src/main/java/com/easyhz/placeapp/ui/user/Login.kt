package com.easyhz.placeapp.ui.user

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easyhz.placeapp.BuildConfig
import com.easyhz.placeapp.R
import com.easyhz.placeapp.ui.component.SimpleIconButton
import com.easyhz.placeapp.ui.component.SpaceDivider
import com.easyhz.placeapp.ui.theme.NaverGreen
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.util.borderBottom
import com.navercorp.nid.NaverIdLoginSDK

@Composable
fun Login(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToBack: () -> Unit
) {
    val context = LocalContext.current
    NaverIdLoginSDK.initialize(context, BuildConfig.NAVER_API_CLIENT_ID, BuildConfig.NAVER_API_CLIENT_ID,"Login") //TODO: clientName 변경 필요

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column {
            LoginHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .borderBottom(color = PlaceAppTheme.colorScheme.primaryBorder, width = 1.dp),
            ) {
                onNavigateToBack()
            }
            LoginContents(context, viewModel)
        }
    }
}

@Composable
private fun LoginHeader(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            SimpleIconButton(
                modifier = Modifier
                    .size(30.dp)
                    .padding(start = 5.dp),
                icon = Icons.Outlined.ArrowBackIos,
                onClick = onBackClick
            )
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(id = R.string.login_header), fontWeight = FontWeight.ExtraBold)
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {

        }
    }
}

@Composable
private fun LoginContents(
    context: Context,
    viewModel: LoginViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 200.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(400.dp),
            painter = painterResource(id = R.drawable.fold_map),
            contentDescription = stringResource(id = R.string.logo_fold_map)
        )
        SpaceDivider(padding = 10)
        SocialLoginButton(
            modifier = Modifier.width(400.dp),
            social = SocialLoginType.NAVER,
            viewModel = viewModel,
            context = context
        )
    }
}

enum class SocialLoginType(
    @DrawableRes val logo: Int,
    @StringRes val text: Int,
    @StringRes val description: Int,
    val color: Color
) {
    NAVER(R.drawable.naver, R.string.login_naver, R.string.login_naver_logo, NaverGreen) {
        override fun onClick(context: Context, viewModel: LoginViewModel) {
            NaverIdLoginSDK.authenticate(context, viewModel.oauthLoginCallback)
        }
    };

    abstract fun onClick(context: Context, viewModel: LoginViewModel)
}

@Composable
private fun SocialLoginButton(
    modifier: Modifier = Modifier,
    context: Context,
    social: SocialLoginType,
    viewModel: LoginViewModel
) {
    Button(
        modifier = modifier.height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = social.color
        ),
        shape = RoundedCornerShape(5.dp),
        onClick = { social.onClick(context, viewModel) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = social.logo),
                contentDescription = stringResource(id = social.description)
            )
            SpaceDivider(padding = 5)
            Text(
                text = stringResource(id = social.text),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
        }
    }
}

@Preview
@Composable
private fun SocialLoginButtonPreview() {
    SocialLoginButton(
        context = LocalContext.current,
        social = SocialLoginType.NAVER,
        viewModel = hiltViewModel()
    )
}
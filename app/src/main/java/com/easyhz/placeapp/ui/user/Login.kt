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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.easyhz.placeapp.R
import com.easyhz.placeapp.domain.model.user.LoginSteps
import com.easyhz.placeapp.ui.component.SpaceDivider
import com.easyhz.placeapp.ui.component.post.LoginHeader
import com.easyhz.placeapp.ui.component.user.UserNameContents
import com.easyhz.placeapp.ui.state.ApplicationState
import com.easyhz.placeapp.ui.theme.KakaoLabel
import com.easyhz.placeapp.ui.theme.KakaoLogo
import com.easyhz.placeapp.ui.theme.KakaoYellow
import com.easyhz.placeapp.ui.theme.NaverGreen
import com.easyhz.placeapp.ui.theme.NaverLabel
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.util.borderBottom

@Composable
fun Login(
    viewModel: LoginViewModel = hiltViewModel(),
    applicationState: ApplicationState,
    onNavigateToBack: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.initNickname()
    }
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
                title = R.string.login_header
            ) {
                viewModel.manageNavigateToBack(onNavigateToBack)
            }
            when(viewModel.userState.step) {
                LoginSteps.LOGIN -> LoginContents(context, viewModel)
                LoginSteps.USERNAME -> UserNameContents()
                LoginSteps.SUCCESS -> {
                    onNavigateToHome()
                    applicationState.showSnackBar(stringResource(id = R.string.login_success))
                }
            }
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
        SpaceDivider(padding = 10)
        SocialLoginButton(
            modifier = Modifier.width(400.dp),
            social = SocialLoginType.KAKAO,
            viewModel = viewModel,
            context = context
        )
    }
}

enum class SocialLoginType(
    @DrawableRes val logo: Int? = null,
    @StringRes val text: Int? = null,
    @StringRes val description: Int? = null,
    val containerColor: Color? = null,
    val contentColor: Color? = null,
    val logoColor: Color? = null,
) {
    NONE() ,
    NAVER(R.drawable.naver, R.string.login_naver, R.string.login_naver_logo, NaverGreen, NaverLabel, NaverLabel) ,
    KAKAO(R.drawable.kakao, R.string.login_kakao, R.string.login_kakao_logo, KakaoYellow, KakaoLabel, KakaoLogo);

    fun onClick(context: Context, viewModel: LoginViewModel) {
        viewModel.login(context, this)
    }
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
            containerColor = social.containerColor!!
        ),
        shape = RoundedCornerShape(5.dp),
        onClick = { social.onClick(context, viewModel) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = social.logo!!),
                contentDescription = stringResource(id = social.description!!),
                tint = social.logoColor!!,
                modifier = Modifier.size(32.dp)
            )
            SpaceDivider(padding = 10)
            Text(
                text = stringResource(id = social.text!!),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = social.contentColor!!
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
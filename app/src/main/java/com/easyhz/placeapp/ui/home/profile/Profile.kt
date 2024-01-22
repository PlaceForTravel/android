package com.easyhz.placeapp.ui.home.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easyhz.placeapp.constants.ContentCardIcons
import com.easyhz.placeapp.constants.PaddingConstants
import com.easyhz.placeapp.domain.model.feed.Content
import com.easyhz.placeapp.domain.model.user.UserManager
import com.easyhz.placeapp.ui.component.ContentCard
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape
import com.easyhz.placeapp.ui.user.LoginViewModel
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK

const val GRID_CELL = 2
@Composable
fun Profile(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp / GRID_CELL
    val dummy = listOf(
        Content(
            boardId = 1,
            imgUrl = listOf("https://picsum.photos/id/307/200/300"),
            nickname = "유저 1",
            regDate = "2023.10.29",
            cityName = "대한민국, 제주특별자치도",
            likeCount = 5,
            text = "제가 이번 추석 연후에 연차까지 내서 빈대? 인정 나도 알아 뉴스는 안봄 하지만 근데 우리 강동구는 괜찮은데 지하철이 개무서움\n" +
                    "자리에 앉기무서운데 그래도 앉아\n" +
                    "현생이 힘드니까",
            places = "제주 흑돼지",
            deletedDate = "",
            modifiedDate = "",
            userId = "유저 1"
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PlaceAppTheme.colorScheme.subBackground)
    ) {
        ProfileView(
            modifier = Modifier.padding(10.dp)
        )
        Button(onClick = { loginViewModel.logout(UserManager.user?.type) }) {
            Text(text = "로그아웃")
        }
        Button(onClick = { NaverIdLoginSDK.logout() }) {
            Text(text = "네이버 로그아웃")
        }
        Button(onClick = {
            UserApiClient.instance.logout {  }
        }) {
            Text(text = "카카오 로그아웃")
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_CELL),
            contentPadding = PaddingValues(1.dp),
            modifier = Modifier
                .background(PlaceAppTheme.colorScheme.subBackground)
        ) {
            itemsIndexed(dummy) {index, item ->
                ContentCard(
                    item = item,
                    imageSize = (screenWidth - 50).dp,
                    cardWidth = screenWidth.dp,
                    isProfile = true,
                    modifier = Modifier
                        .width(screenWidth.dp)
                        .padding(PaddingConstants.PROFILE_CONTENT_ALL.dp)
                        .clip(roundShape)
                        .background(PlaceAppTheme.colorScheme.mainBackground)
                        .clickable { },
                )
            }
        }
    }
}

@Composable
private fun ProfileView(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ContentCardIcons.USER.icon,
                contentDescription = stringResource(id = ContentCardIcons.USER.label),
                tint = LocalContentColor.current,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "유저",
                style = TextStyle.Default.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false,
                    )
                ),
            )
        }
    }
}


@Preview
@Composable
private fun ProfilePreview() {
    Profile()
}
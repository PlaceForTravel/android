package com.easyhz.placeapp.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.easyhz.placeapp.R
import com.easyhz.placeapp.constants.ContentCardIcons
import com.easyhz.placeapp.constants.PaddingConstants.ICON_TEXT_HORIZONTAL
import com.easyhz.placeapp.constants.PaddingConstants.ICON_TEXT_VERTICAL
import com.easyhz.placeapp.constants.PaddingConstants.IMAGE_HORIZONTAL
import com.easyhz.placeapp.constants.PaddingConstants.TEXT_HORIZONTAL
import com.easyhz.placeapp.domain.model.feed.Content
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape
import com.easyhz.placeapp.util.getImageRequestDefault
import com.easyhz.placeapp.util.toTimeFormat

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    item: Content,
    cardWidth: Dp = LocalConfiguration.current.screenWidthDp.dp,
    imageSize: Dp =  (LocalConfiguration.current.screenWidthDp - 100).dp,
    contentDescription: String = "IMG",
    isProfile: Boolean = false,
) {
    val imagesCount = item.imgUrl.size
    val pagerState = rememberPagerState { imagesCount }
    Box(
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            PlaceContentInfo(item.cityName)
            ImageSlider(pagerState = pagerState, itemsCount = imagesCount) {index ->
                ContentImage(
                    imagePath = item.imgUrl[index],
                    imageSize = imageSize,
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .width(cardWidth)
                        .padding(horizontal = IMAGE_HORIZONTAL.dp)
                        .clip(roundShape)
                        .align(Alignment.CenterHorizontally)
                )
            }
            if (!isProfile) {
                ContentInfo(name = item.nickname, regDate = item.regDate, likeCount = item.likeCount)
            }
            SpaceDivider(10)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailContentCard(
    modifier: Modifier = Modifier,
    item: FeedDetail,
    cardWidth: Dp = LocalConfiguration.current.screenWidthDp.dp,
    imageSize: Dp =  (LocalConfiguration.current.screenWidthDp - 100).dp,
    contentDescription: String = "IMG",
    onMapClick: () -> Unit = { }
) {
    val imagesCount = item.placeResponseDTOS.size
    val pagerState = rememberPagerState { imagesCount }
    Box(
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            PlaceContentInfo(item.cityName)
            Row(
                modifier = Modifier.padding(horizontal = TEXT_HORIZONTAL.dp)
            ) {
                Text(text = stringResource(id = R.string.place_icon), modifier = Modifier.padding(end = 13.dp))
                Text(text = item.placeResponseDTOS[pagerState.currentPage].placeName)
            }
            SpaceDivider(padding = 5)
            ImageSlider(pagerState = pagerState, itemsCount = imagesCount) { index ->
                ContentImage(
                    imagePath = item.placeResponseDTOS[index].imgUrl,
                    imageSize = imageSize,
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .width(cardWidth)
                        .padding(horizontal = IMAGE_HORIZONTAL.dp)
                        .clip(roundShape)
                        .align(Alignment.CenterHorizontally)
                )
            }
            ContentInfo(name = item.nickname, regDate = item.regDate, likeCount = item.likeCount)
            SpaceDivider(10)
            Text(
                item.content,
                modifier = Modifier.padding(horizontal = TEXT_HORIZONTAL.dp)
            )
            SimpleIconButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = ICON_TEXT_HORIZONTAL.dp),
                icon = ContentCardIcons.MAP.icon,
                contentDescription = stringResource(id = ContentCardIcons.MAP.label),
                onClick = onMapClick
            )
            SpaceDivider(10)
        }
    }
}
@Composable
fun IconText(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconSize: Dp = 36.dp,
    tint: Color = LocalContentColor.current,
    text: String,
    contentDescription: String,
    onClick: () -> Unit,
    textStyle: TextStyle = TextStyle.Default.copy(
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Justify,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false,
        )
    ),
) {
    Box(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            SimpleIconButton(
                icon = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(iconSize),
                tint = tint,
                onClick = onClick,
            )
            Text(
                text = text,
                style = textStyle,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}

@Composable
private fun ContentInfo(
    name: String,
    regDate: String,
    likeCount: Int,
    cardWidth: Dp = LocalConfiguration.current.screenWidthDp.dp,
) {
    Row(
        modifier = Modifier.width(cardWidth),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconText(
            icon = ContentCardIcons.USER.icon,
            text = name,
            contentDescription = stringResource(id = ContentCardIcons.USER.label),
            onClick = { },
            modifier = Modifier.padding(horizontal = ICON_TEXT_HORIZONTAL.dp, vertical = ICON_TEXT_VERTICAL.dp)
        )
        Text(regDate.toTimeFormat(), modifier = Modifier.padding(horizontal = TEXT_HORIZONTAL.dp))
    }
    IconText(
        icon = ContentCardIcons.BOOKMARK.icon,
        text = likeCount.toString(),
        contentDescription = stringResource(id = ContentCardIcons.BOOKMARK.label),
        onClick = { },
        modifier = Modifier.padding(horizontal = ICON_TEXT_HORIZONTAL.dp)
    )
}

@Composable
private fun PlaceContentInfo(name: String) {
    IconText(
        icon = ContentCardIcons.PLACE.icon,
        text = name,
        contentDescription = stringResource(id = ContentCardIcons.PLACE.label),
        onClick = { },
        modifier = Modifier.padding(horizontal = ICON_TEXT_HORIZONTAL.dp, vertical = ICON_TEXT_VERTICAL.dp)
    )
}

@Composable
private fun ContentImage(
    modifier: Modifier = Modifier,
    imagePath: String?,
    imageSize: Dp,
    contentDescription: String = "IMG",
) {
    val imageRequest = getImageRequestDefault(data = imagePath, context = LocalContext.current)
    var image by remember { mutableStateOf(imageRequest) }
    val painter = rememberAsyncImagePainter(model = image)

    Box(
        modifier = modifier
    ) {
        ImageLoader(
            image = image,
            contentDescription = contentDescription,
            modifier = modifier
                .width(imageSize)
                .height(imageSize)
                .align(Alignment.Center)
        )
        if (painter.state is AsyncImagePainter.State.Error) {
            ErrorImage(
                onClick = {
                    image = imageRequest
                },
                modifier = modifier
                    .height(imageSize)
                    .width(imageSize)
            )
        }
    }
}


@Preview("Feed Case")
@Composable
private fun CardPreview() {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val mock = Content(
        boardId = 1,
        imgUrl = listOf("https://picsum.photos/id/307/200/300"),
        nickname = "유저 1",
        regDate = "2023.10.29",
        cityName = "대한민국, 제주특별자치도",
        likeCount = 5,
        text = null,
        places = null,
        deletedDate = "",
        modifiedDate = "",
        userId = "유저 1"
    )
    PlaceAppTheme {
        ContentCard(
            item = mock,
            imageSize = screenWidthDp.dp,
            cardWidth = screenWidthDp.dp,
            modifier = Modifier.width(screenWidthDp.dp),
        )
    }
}
@Preview("Detail Case")
@Composable
private fun DetailCardPreview() {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val mock = Content(
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
    PlaceAppTheme {
        ContentCard(
            item = mock,
            imageSize = screenWidthDp.dp,
            cardWidth = screenWidthDp.dp,
            modifier = Modifier.width(screenWidthDp.dp),
        )
    }
}
//
//@Preview("Error Image")
//@Composable
//private fun ErrorImagePreview() {
//    PlaceAppTheme {
//        ErrorImage(
//            modifier = Modifier
//                .height(450.dp)
//                .width(450.dp)
//        ) {
//        }
//    }
//}
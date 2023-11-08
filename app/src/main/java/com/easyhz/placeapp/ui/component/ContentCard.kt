package com.easyhz.placeapp.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.PersonPinCircle
import androidx.compose.material.icons.outlined.Place
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
import coil.request.ImageRequest
import com.easyhz.placeapp.R
import com.easyhz.placeapp.constants.PaddingConstants.ICON_TEXT_HORIZONTAL
import com.easyhz.placeapp.constants.PaddingConstants.ICON_TEXT_VERTICAL
import com.easyhz.placeapp.constants.PaddingConstants.IMAGE_HORIZONTAL
import com.easyhz.placeapp.constants.PaddingConstants.TEXT_HORIZONTAL
import com.easyhz.placeapp.ui.home.feed.FeedType
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    item: FeedType,
    cardWidth: Dp = LocalConfiguration.current.screenWidthDp.dp,
    imageSize: Dp =  (LocalConfiguration.current.screenWidthDp - 100).dp,
    contentDescription: String = "IMG",
    onMapClick: () -> Unit = { }
) {
    Box(
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            IconText(
                icon = ContentCardIconSections.PLACE.icon,
                text = item.placeName,
                contentDescription = stringResource(id = ContentCardIconSections.PLACE.label),
                onClick = { },
                modifier = Modifier.padding(horizontal = ICON_TEXT_HORIZONTAL.dp, vertical = ICON_TEXT_VERTICAL.dp)
            )
            // TODO: Confirm
            item.detailPlace?.let {
                Row(
                    modifier = Modifier.padding(horizontal = TEXT_HORIZONTAL.dp)
                ) {
                    Text(stringResource(id = R.string.content_place), fontWeight = FontWeight.ExtraLight, color = Color.Gray)
                    Text(
                        it,
                    )
                }
                SpaceDivider(padding = 5)
            }
            ContentImage(
                imagePath = item.imagePath,
                imageSize = imageSize,
                contentDescription = contentDescription,
                modifier = Modifier
                    .width(cardWidth)
                    .padding(horizontal = IMAGE_HORIZONTAL.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.width(cardWidth),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconText(
                    icon = ContentCardIconSections.USER.icon,
                    text = item.userName,
                    contentDescription = stringResource(id = ContentCardIconSections.USER.label),
                    onClick = { },
                    modifier = Modifier.padding(horizontal = ICON_TEXT_HORIZONTAL.dp, vertical = ICON_TEXT_VERTICAL.dp)
                )
                Text(item.regDate, modifier = Modifier.padding(horizontal = TEXT_HORIZONTAL.dp))
            }
            IconText(
                icon = ContentCardIconSections.BOOKMARK.icon,
                text = item.bookmarkCount.toString(),
                contentDescription = stringResource(id = ContentCardIconSections.BOOKMARK.label),
                onClick = { },
                modifier = Modifier.padding(horizontal = ICON_TEXT_HORIZONTAL.dp)
            )
            item.content?.let {
                SpaceDivider(10)
                Text(
                    it,
                    modifier = Modifier.padding(horizontal = TEXT_HORIZONTAL.dp)
                )
                SimpleIconButton(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = ICON_TEXT_HORIZONTAL.dp),
                    icon = Icons.Outlined.Map,
                    contentDescription = stringResource(id = R.string.content_map_icon),
                    onClick = onMapClick
                )
            }
            SpaceDivider(10)
        }
    }
}

enum class ContentCardIconSections(
    @StringRes val label: Int,
    val icon: ImageVector
) {
    PLACE(R.string.content_place_icon, Icons.Outlined.Place,),
    USER(R.string.content_user_icon, Icons.Outlined.PersonPinCircle),
    BOOKMARK(R.string.content_bookmark_icon, Icons.Outlined.BookmarkBorder),
    MAP(R.string.content_map_icon, Icons.Outlined.Map),
    REFRESH(R.string.image_refresh, Icons.Filled.Refresh)
}

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    icon: ImageVector,
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
                modifier = Modifier.size(36.dp),
                onClick = onClick
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
private fun ContentImage(
    modifier: Modifier = Modifier,
    imagePath: String?,
    imageSize: Dp,
    contentDescription: String = "IMG",
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(imagePath)
        .crossfade(true)
        .build()
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

@Composable
private fun ErrorImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        SimpleIconButton(
            modifier = Modifier.align(Alignment.Center),
            icon = ContentCardIconSections.REFRESH.icon,
            contentDescription = stringResource(id = ContentCardIconSections.REFRESH.label),
            onClick = onClick
        )
    }
}


@Preview("Feed Case")
@Composable
private fun CardPreview() {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val mock = FeedType(
        id = 1,
        imagePath = "https://picsum.photos/id/307/200/300",
        userName = "유저 1",
        regDate = "2023.10.29",
        placeName= "대한민국, 제주특별자치도",
        bookmarkCount = 5,
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
    val mock = FeedType(
        id = 1,
        imagePath = "https://picsum.photos/id/307/200/300",
        userName = "유저 1",
        regDate = "2023.10.29",
        placeName= "대한민국, 제주특별자치도",
        bookmarkCount = 5,
        content = "제가 이번 추석 연후에 연차까지 내서 빈대? 인정 나도 알아 뉴스는 안봄 하지만 근데 우리 강동구는 괜찮은데 지하철이 개무서움\n" +
                "자리에 앉기무서운데 그래도 앉아\n" +
                "현생이 힘드니까",
        detailPlace = "제주 흑돼지"
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
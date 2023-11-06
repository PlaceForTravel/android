package com.easyhz.placeapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.PersonPinCircle
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cardWidth: Dp,
    userName: String,
    regDate: String,
    placeName: String,
    bookmarkCount: Int,
    imagePath: String?,
    imageSize: Dp,
    contentDescription: String = "IMG",
) {
    Surface(
        modifier = modifier,
        onClick = onClick
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            IconText(
                icon = Icons.Outlined.Place,
                text = placeName,
                contentDescription = "Place",
                onClick = { },
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            )
            ContentImage(
                imagePath = imagePath,
                imageSize = imageSize,
                contentDescription = contentDescription,
                modifier = Modifier
                    .width(cardWidth)
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.width(cardWidth),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconText(
                    icon = Icons.Outlined.PersonPinCircle,
                    text = userName,
                    contentDescription = "UserName",
                    onClick = { },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                )
                Text(regDate, modifier = Modifier.padding(25.dp))
            }
            IconText(
                icon = Icons.Outlined.BookmarkBorder,
                text = bookmarkCount.toString(),
                contentDescription = "BookmarkCount",
                onClick = { },
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
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
            icon = Icons.Filled.Refresh,
            contentDescription = "Refresh",
            onClick = onClick
        )
    }
}


@Preview("Image Example")
@Composable
private fun CardPreview() {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    PlaceAppTheme {
        ContentCard(
            onClick = { },
            userName = "유저 1",
            placeName = "대한민국, 제주특별자치도, 서귀포시",
            regDate = "2023.11.06",
            bookmarkCount = 2520,
            imagePath = null,
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
package com.easyhz.placeapp.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.easyhz.placeapp.R
import com.easyhz.placeapp.constants.ContentCardIcons
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.util.getImageRequestDefault

@Composable
fun ImageLoader(
    image: ImageRequest,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = image,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        filterQuality = FilterQuality.None,
        placeholder = painterResource(id = R.drawable.ic_launcher_background),
        modifier = modifier
    )
}


@Composable
fun ErrorImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        SimpleIconButton(
            modifier = Modifier.align(Alignment.Center),
            icon = ContentCardIcons.REFRESH.icon,
            contentDescription = stringResource(id = ContentCardIcons.REFRESH.label),
            onClick = onClick
        )
    }
}

/**
 * Image Slider Implementation
 * Dot(..), DotsIndicator(..)
 *
 * : https://blog.protein.tech/jetpack-compose-auto-image-slider-with-dots-indicator-45dfeba37712
 *
 */

@ExperimentalFoundationApi
@Composable
fun ImageSlider(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    itemsCount: Int,
    content: @Composable (index: Int) -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        HorizontalPager(state = pagerState) {page ->
            content(page)
        }
        if( itemsCount > 1) {
            Surface(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.TopCenter),
                shape = CircleShape,
                color = PlaceAppTheme.colorScheme.mainBackground.copy(alpha = 0.8f)
            ) {
                DotsIndicator(
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp),
                    total = itemsCount,
                    selected = pagerState.currentPage,
                    dotSize = 8.dp
                )
            }
        }
    }

}

@Composable
private fun Dot(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
private fun DotsIndicator(
    modifier: Modifier = Modifier,
    total: Int,
    selected: Int,
    selectedColor : Color = PlaceAppTheme.colorScheme.primary,
    unSelectedColor: Color = PlaceAppTheme.colorScheme.unselectedIcon,
    dotSize: Dp,
) {
    LazyRow(
        modifier = modifier
    ) {
        items(total) { index ->
            Dot(
                size = dotSize,
                color = if(index == selected) selectedColor else unSelectedColor
            )

            if (index != total - 1) {
                SpaceDivider(2)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(name = "Default")
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ImageSliderPreview() {
    val pagerState = rememberPagerState{ 3 }
    val images = listOf("", "", "")
    val imageRequest = getImageRequestDefault(data = images, context = LocalContext.current)
    PlaceAppTheme {
        ImageSlider(pagerState = pagerState, itemsCount = 3, modifier = Modifier.width(300.dp).height(300.dp)) {
            ImageLoader(image = imageRequest, contentDescription = "", modifier = Modifier.width(300.dp).height(300.dp))
        }
    }
}

@Preview(name = "Default")
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun DotsIndicatorPreview() {
    PlaceAppTheme {
        DotsIndicator(total = 5, selected = 2, dotSize = 10.dp, modifier = Modifier.padding(10.dp))
    }
}
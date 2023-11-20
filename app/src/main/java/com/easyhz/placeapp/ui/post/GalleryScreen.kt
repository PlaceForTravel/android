package com.easyhz.placeapp.ui.post

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.easyhz.placeapp.R
import com.easyhz.placeapp.gallery.Gallery
import com.easyhz.placeapp.ui.component.ImageLoader
import com.easyhz.placeapp.ui.component.SpaceDivider
import com.easyhz.placeapp.ui.component.detail.WindowShade
import com.easyhz.placeapp.ui.component.post.PostHeader
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.util.getImageRequestDefault

const val GRID_CELL = 4

@Composable
fun GalleryScreen(
    viewModel: NewPostViewModel = hiltViewModel(),
    onNavigateToBack: () -> Unit,
    onNavigateToNext: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val pagingImageList = viewModel.imageList.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.getGalleryImages()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PlaceAppTheme.colorScheme.mainBackground),
    ) {
        PostHeader(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            title = stringResource(id = R.string.post_gallery_header),
            onBackClick = { onNavigateToBack() },
            onNextClick = {
                if(viewModel.selectedImageList.size == 0) Log.d("GalleryScreen", "한 장 이상 선택")// 사진 한 장 이상 선택 스낵바
                else {
                    onNavigateToNext()
                    viewModel.initPlaceList()
                }
            }
        )
        if (pagingImageList.itemCount == 0) {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(id = R.string.image_load_error),
                    fontSize = 19.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        } else {
            val find = viewModel.currentImage.value ?: pagingImageList[0]
            val imageRequest = getImageRequestDefault(data = find?.uri, context = LocalContext.current)

            ImageLoader(image = imageRequest, contentDescription = find?.name ?: "currentImage", modifier = Modifier.size(screenWidth.dp))
            SpaceDivider(padding = 10)
            LazyVerticalGrid(
                modifier = Modifier
                    .background(PlaceAppTheme.colorScheme.mainBackground),
                columns = GridCells.Fixed(GRID_CELL),
                contentPadding = PaddingValues(1.dp)
            ) {
                items(pagingImageList.itemCount) { index ->
                    pagingImageList[index]?.let { image ->
                        GalleryItem(
                            image = image,
                            onSelect = {
                                viewModel.manageSelectImage(image.id, image)
                            },
                            isCurrent = viewModel.currentImage.value == image,
                            selectedImages = viewModel.selectedImageList,
                            modifier = Modifier
                                .size((screenWidth / GRID_CELL).dp)
                                .padding(0.5.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GalleryItem(
    modifier: Modifier = Modifier,
    image: Gallery,
    selectedImages: List<Gallery>,
    onSelect: () -> Unit,
    isCurrent: Boolean,
) {
    val index = selectedImages.indexOf(image)
    val isSelected = index != -1
    val imageRequest = getImageRequestDefault(data = image.uri, LocalContext.current)
    Box(
        modifier = modifier.clickable {
            onSelect()
        }
    ) {
        ImageLoader(
            image = imageRequest,
            contentDescription = image.name,
            modifier = modifier
        )
        if(isCurrent) WindowShade()
        SelectBox(
            selected = isSelected,
            size = 24,
            index = if (isSelected) index + 1 else null,
            modifier = Modifier.padding(10.dp),
        )
    }
}

@Composable
private fun SelectBox(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    size: Int = 32,
    index: Int?,
) {
    val backGroundColor = if(selected) PlaceAppTheme.colorScheme.primary else PlaceAppTheme.colorScheme.transparent

    Box(modifier = modifier
        .size(size.dp)
        .border(
            width = 1.dp,
            color = Color.White,
            shape = CircleShape
        )
        .clip(CircleShape)
        .background(backGroundColor)
    ) {
        if (selected) Text(text = "$index", modifier = Modifier.align(Alignment.Center), color = Color.White, fontSize = (size / 2).sp)
    }
}


@Preview
@Composable
private fun ImageSelectBoxPreview() {
    PlaceAppTheme {
        SelectBox(
            selected = true,
            index = 1
        )
    }
}
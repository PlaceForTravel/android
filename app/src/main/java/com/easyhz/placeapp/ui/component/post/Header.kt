package com.easyhz.placeapp.ui.component.post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easyhz.placeapp.R
import com.easyhz.placeapp.ui.component.SimpleIconButton
import com.easyhz.placeapp.ui.theme.PlaceAppTheme


@Composable
fun PostHeader(
    modifier: Modifier = Modifier,
    title: String,
    next: String = stringResource(id = R.string.post_next_header),
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
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
            Text(text = title, fontWeight = FontWeight.ExtraBold)
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            TextButton(
                onClick = onNextClick,
            ) {
                Text(text = next, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = PlaceAppTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
fun BookmarkHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.bookmark_header),
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 5.dp, start = 20.dp)
            )
        }
    }
}

@Composable
fun LoginHeader(
    modifier: Modifier = Modifier,
    title: Int,
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
            Text(text = stringResource(id = title), fontWeight = FontWeight.ExtraBold)
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {

        }
    }
}

@Preview
@Composable
private fun PostHeaderPreview() {
    PlaceAppTheme {
        PostHeader(
            modifier = Modifier.fillMaxWidth(),
            title = "사진 선택",
            onBackClick = { },
            onNextClick = { },
            next = "다음"
        )
    }
}

@Preview
@Composable
private fun BookmarkHeaderPreview() {
    PlaceAppTheme {
        BookmarkHeader()
    }
}
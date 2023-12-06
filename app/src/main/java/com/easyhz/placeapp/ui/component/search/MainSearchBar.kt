package com.easyhz.placeapp.ui.component.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easyhz.placeapp.R
import com.easyhz.placeapp.constants.PaddingConstants.CONTENT_ALL
import com.easyhz.placeapp.constants.PaddingConstants.SEARCH_BAR_HORIZONTAL
import com.easyhz.placeapp.constants.SearchIcons
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@Composable
fun MainSearchBar(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = { },
    isFocus: Boolean = false,
    enabled: Boolean = true,
    onSearch: () -> Unit = { },
    onCanceled: () -> Unit = { },
    searchHistory: List<String> = emptyList()
) {
//    var text by remember { mutableStateOf("") }
//    var active by remember { mutableStateOf(false) }
//    val items = remember { mutableListOf<String>() }
//    var focus by remember { mutableStateOf(false) }
    Column {
        TextField(
            modifier = modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
//                text = it
//                active = text.isNotEmpty()
//            },
            placeholder = {
                Text(text = stringResource(id = R.string.search_comment))
            },
            leadingIcon = {
                Icon(
                    imageVector = SearchIcons.SEARCH.icon,
                    contentDescription = stringResource(id = SearchIcons.SEARCH.label)
                )
            },
            enabled = enabled,
            trailingIcon = {
                if (isFocus && value.isNotEmpty()) {
                    Icon(
                        modifier = Modifier.clickable {
                            onCanceled()
                        },
                        imageVector = SearchIcons.CANCEL.icon,
                        contentDescription = stringResource(id = SearchIcons.CANCEL.label)
                    )
                }
            },
            colors = setSearchBarColors(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() })
        )
        if (isFocus) {
            searchHistory.forEach {
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .clickable {
                            onValueChange(it)
                        }
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = SearchIcons.HISTORY.icon,
                        contentDescription = stringResource(id = SearchIcons.HISTORY.label)
                    )
                    Text(text = it, modifier = Modifier.weight(1F))
                    Icon(imageVector = SearchIcons.CLOSE.icon, contentDescription = stringResource(id = SearchIcons.CLOSE.label))
                }
            }
        }
    }
}

@Composable
fun SubSearchBar(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(PlaceAppTheme.colorScheme.subBackground)
            .padding(CONTENT_ALL.dp)
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = PlaceAppTheme.colorScheme.unselectedIcon,
                shape = CircleShape
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape)
                .background(PlaceAppTheme.colorScheme.subBackground)
                .padding(horizontal = SEARCH_BAR_HORIZONTAL.dp)
                .height(TextFieldDefaults.MinHeight - 10.dp)
                .clickable {
                    onClick()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .height(TextFieldDefaults.MinHeight)
                    .padding(horizontal = 10.dp),
                imageVector = SearchIcons.SEARCH.icon,
                contentDescription = stringResource(id = SearchIcons.SEARCH.label)
            )
            Text(
                text = stringResource(id = R.string.search_comment),
                style = TextStyle.Default.copy(
                    fontSize = 15.sp,
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

@Composable
fun setSearchBarColors() = TextFieldDefaults.colors(
    focusedContainerColor = PlaceAppTheme.colorScheme.transparent,
    unfocusedContainerColor = PlaceAppTheme.colorScheme.transparent,
    disabledContainerColor = PlaceAppTheme.colorScheme.transparent,
    cursorColor = PlaceAppTheme.colorScheme.mainText,
    focusedIndicatorColor = PlaceAppTheme.colorScheme.transparent,
    unfocusedIndicatorColor = PlaceAppTheme.colorScheme.transparent,
    disabledIndicatorColor = PlaceAppTheme.colorScheme.transparent,
)

@Preview
@Composable
private fun MainSearchBarPreview() {
    MainSearchBar()
}

@Preview
@Composable
private fun SubSearchBarPreview() {
    SubSearchBar( onClick = { })
}

package com.easyhz.placeapp.ui.component.post

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.PlainTooltipState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easyhz.placeapp.R
import com.easyhz.placeapp.constants.ContentCardIcons
import com.easyhz.placeapp.domain.model.place.PlaceItem
import com.easyhz.placeapp.ui.component.CircleDivider
import com.easyhz.placeapp.ui.component.SpaceDivider
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape
import com.easyhz.placeapp.util.borderBottom
import com.easyhz.placeapp.util.toBasicCategory
import com.easyhz.placeapp.util.withoutHTML
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSearchModal(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onActiveChange: (Boolean) -> Unit,
    placeList: List<PlaceItem>?,
    isEqualCity: Boolean = false,
    cityName: String = "",
    onItemClick: (PlaceItem) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val tooltipState = remember { PlainTooltipState() }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Card(
        modifier = modifier
            .padding(30.dp)
            .padding(top = 100.dp),
    ) {
        Column(
            modifier = modifier
                .background(color = PlaceAppTheme.colorScheme.mainBackground)
                .clip(roundShape)
        ) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(roundShape)
                    .background(PlaceAppTheme.colorScheme.mainBackground)
                    .border(
                        width = 1.dp,
                        color = PlaceAppTheme.colorScheme.secondaryBorder,
                        shape = roundShape,
                    )
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { if (it.isFocused) onActiveChange(true) },
                    value = query,
                    onValueChange = onQueryChange,
                    trailingIcon = {
                        Row {
                            Icon(
                                modifier = Modifier.clickable { onSearch() },
                                imageVector = Icons.Outlined.Search,
                                contentDescription = "MapSearch"
                            )
                        }
                    },
                    placeholder = { Text(text = stringResource(id = R.string.place_search)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = PlaceAppTheme.colorScheme.transparent,
                        unfocusedContainerColor = PlaceAppTheme.colorScheme.transparent,
                        disabledContainerColor = PlaceAppTheme.colorScheme.transparent,
                        cursorColor = PlaceAppTheme.colorScheme.mainText,
                        focusedIndicatorColor = PlaceAppTheme.colorScheme.transparent,
                        unfocusedIndicatorColor = PlaceAppTheme.colorScheme.transparent,
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { onSearch() })
                )
            }
            CityNameContent(cityName, isEqualCity, tooltipState)
            if (placeList.isNullOrEmpty()) {
                NoData()
            } else {
                PlaceListContainer(
                    placeList = placeList,
                    tooltipState = tooltipState,
                    isEqualCity = isEqualCity,
                    onItemClick = onItemClick,
                )
            }
        }

    }
}

@Composable
private fun NoData(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = R.string.no_search_data))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlaceListContainer(
    placeList: List<PlaceItem>,
    tooltipState: PlainTooltipState,
    isEqualCity: Boolean,
    onItemClick: (PlaceItem) -> Unit
) {
    val scope = rememberCoroutineScope()
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .borderBottom(
                    width = 0.7.dp,
                    color = PlaceAppTheme.colorScheme.secondaryBorder
                )
                .padding(horizontal = 20.dp)
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContentText(text = "검색 결과", )
            CircleDivider(radius = 1, containerSize = 20)
            ContentText(text = "${placeList.size} 건")
        }
    }
    LazyColumn(
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        items(placeList) { item ->
            PlaceItem(
                item = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .borderBottom(
                        color = PlaceAppTheme.colorScheme.secondaryBorder,
                        width = 0.3.dp
                    )
                    .padding(20.dp)
                    .clickable {
                        onItemClick(item)
                        scope.launch {
                            if (!isEqualCity) {
                                tooltipState.show()
                            }
                        }
                    }
            )
        }
        item {  SpaceDivider(padding = 20) }
    }
}

@Composable
private fun PlaceItem(
    modifier: Modifier = Modifier,
    item: PlaceItem
) {
    val address = item.roadAddress.ifEmpty { item.address }
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContentText(text = item.title.withoutHTML(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            CircleDivider(radius = 1, containerSize = 20)
            ContentText(text = item.category.toBasicCategory())
        }
        ContentText(text = address)
    }
}

@Composable
private fun ContentText(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Medium,
    fontSize: TextUnit = 15.sp
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle.Default.copy(
            textAlign = TextAlign.Justify,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
            fontWeight = fontWeight,
            fontSize = fontSize
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CityNameContent(
    cityName: String,
    isEqualCity: Boolean,
    tooltipState: PlainTooltipState
) {
    if(cityName.isNotEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 10.dp)
        ){
            Text(text = "${stringResource(id = R.string.selected_city)}: $cityName", fontWeight = FontWeight.Bold)
            if (!isEqualCity) {
                PlainTooltipBox(
                    tooltip = { Text(text = stringResource(id = R.string.notice_please_select_same_city)) },
                    tooltipState = tooltipState
                ) {
                    Icon(
                        imageVector = ContentCardIcons.ERROR.icon,
                        contentDescription = stringResource(id = ContentCardIcons.ERROR.label),
                        tint = PlaceAppTheme.colorScheme.error,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(20.dp)
                            .tooltipAnchor()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PlaceItemPreview() {
    PlaceAppTheme {
        PlaceItem(
            modifier = Modifier,
            PlaceItem(
                title = "맛집" ,
                link= "",
                category = "음식점>한식>육류,고기요리>닭볶음탕",
                description = "",
                telephone = "",
                address = "경기도 수원시 팔달구 매산로2가 27-14 1층 홍미집",
                roadAddress = "경기도 수원시 팔달구 향교로 27-1 1층 홍미집",
                mapx = 0,
                mapy = 0
            )
        )
    }
}
@Preview
@Composable
private fun MapSearchModalNoDataPreview() {
    PlaceAppTheme {
        MapSearchModal(
//            context = LocalContext.current,
            query = "맛집",
            onQueryChange = { },
            onSearch = { },
//            active = false,
            onActiveChange = { },
//            onCancel = { },
            placeList = listOf(),
            modifier = Modifier.height(760.dp),
            onItemClick = { }
        )
    }
}
@Preview
@Composable
private fun MapSearchModalPreview() {
    PlaceAppTheme {
        MapSearchModal(
//            context = LocalContext.current,
            query = "맛집",
            onQueryChange = { },
            onSearch = { },
//            active = false,
            onActiveChange = { },
//            onCancel = { },
            isEqualCity = false,
            cityName = "경기도 수원시",
            placeList = listOf(
                PlaceItem(
                title = "맛집" ,
                link= "",
                category = "음식점>한식>육류,고기요리>닭볶음탕",
                description = "",
                telephone = "",
                address = "경기도 수원시 팔달구 매산로2가 27-14 1층 홍미집",
                roadAddress = "경기도 수원시 팔달구 향교로 27-1 1층 홍미집",
                mapx = 0,
                mapy = 0
            )
            ),
            modifier = Modifier.height(760.dp),
            onItemClick = { }
        )
    }
}
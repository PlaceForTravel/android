package com.easyhz.placeapp.ui.home.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.easyhz.placeapp.ui.component.search.MainSearchBar
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.util.borderBottom

@Composable
fun Search() {
    var isFocus by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }
    MainSearchBar(
        modifier = Modifier
            .borderBottom(PlaceAppTheme.colorScheme.secondaryBorder, 1.dp)
            .padding(5.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocus = it.isFocused
            },
        isFocus = isFocus,
        enabled = true,
        onSearch = {
            isFocus = false
            focusManager.clearFocus()
        },
    )
    if (!isFocus) {
        Text(text = "검색 결과")
    }
}
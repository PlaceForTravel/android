package com.easyhz.placeapp.ui.home.search

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.easyhz.placeapp.ui.component.search.MainSearchBar
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.util.borderBottom

@Composable
fun Search() {
    var text by remember { mutableStateOf("") }
    val items = remember { mutableListOf<String>() }
    var focus by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    MainSearchBar(
        modifier = Modifier
            .borderBottom(PlaceAppTheme.colorScheme.secondaryBorder, 1.dp)
            .padding(5.dp)
            .onFocusChanged {
                println("focus")
                focus = it.isFocused
            },
        value = text,
        onValueChange = { text = it },
        focus = focus,
        enabled = true,
        onSearch = {
            println("onSearch")
            items.add(text)
            focusManager.clearFocus()
            focus = false
        },
        onCanceled = { text = "" },
        searchHistory = items
    )

}
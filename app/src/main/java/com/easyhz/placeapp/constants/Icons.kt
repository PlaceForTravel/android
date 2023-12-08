package com.easyhz.placeapp.constants

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FmdBad
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.PersonPinCircle
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Send
import androidx.compose.ui.graphics.vector.ImageVector
import com.easyhz.placeapp.R


enum class ContentCardIcons(
    @StringRes val label: Int,
    val icon: ImageVector
) {
    PLACE(R.string.content_place_icon, Icons.Outlined.Place,),
    USER(R.string.content_user_icon, Icons.Outlined.PersonPinCircle),
    BOOKMARK(R.string.content_bookmark_icon, Icons.Outlined.BookmarkBorder),
    MAP(R.string.content_map_icon, Icons.Outlined.Map),
    REFRESH(R.string.image_refresh, Icons.Filled.Refresh),

    ADD(R.string.home_add, Icons.Outlined.Edit),

    ERROR(R.string.setting_error, Icons.Outlined.FmdBad),

    SEND(R.string.post_add_comment, Icons.Outlined.Send)
}

enum class SearchIcons(
    @StringRes val label: Int,
    val icon: ImageVector
) {
    SEARCH(R.string.search_icon, Icons.Outlined.Search),
    CANCEL(R.string.search_cancel_icon, Icons.Outlined.Cancel),
    HISTORY(R.string.search_history_icon, Icons.Outlined.History),
    CLOSE(R.string.search_close_icon, Icons.Outlined.Close),
}

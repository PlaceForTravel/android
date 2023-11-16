package com.easyhz.placeapp.constants

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.PersonPinCircle
import androidx.compose.material.icons.outlined.Place
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

    ADD(R.string.home_add, Icons.Outlined.Edit)
}

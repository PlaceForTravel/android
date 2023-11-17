package com.easyhz.placeapp.ui.navigation

object PostRoutes {
    const val GALLERY = "gallery"
    const val NEW_POST = "new"
}

enum class NewPostOrder(
    val route: String
) {
    GALLERY(route = "${MainDestinations.NEW_POST_ROUTE}/${PostRoutes.GALLERY}"),
    NEW_POST(route = "${MainDestinations.NEW_POST_ROUTE}/${PostRoutes.NEW_POST}"),
    COMPLETE(route = HomeSections.FEED.route)
}
package com.easyhz.placeapp.ui.navigation

object PostRoutes {
    const val GALLERY = "gallery"
    const val NEW_POST = "new"
    const val MODIFY = "modify"
}

enum class NewPostOrder(
    val route: String
) {
    GALLERY(route = "${MainDestinations.NEW_POST_ROUTE}/${PostRoutes.GALLERY}"),
    NEW_POST(route = "${MainDestinations.NEW_POST_ROUTE}/${PostRoutes.NEW_POST}"),
    COMPLETE(route = HomeSections.FEED.route)
}

object UserRoutes {
    const val LOGIN = "login"
}
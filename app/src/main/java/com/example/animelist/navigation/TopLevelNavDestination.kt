package com.example.animelist.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.animelist.R
import com.example.animelist.ui.feature.dashboard.navigation.DashboardScreen
import com.example.animelist.ui.feature.interests.navigation.InterestsScreen
import com.example.animelist.ui.feature.watchlater.navigation.WatchLaterScreen
import kotlin.reflect.KClass

enum class TopLevelNavDestination(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route
) {
    DASH_BOARD(
        icon = R.drawable.ic_home,
        title = R.string.top_nav_dash_board,
        route = DashboardScreen::class
    ),
    WATCH_LATER(
        icon = R.drawable.ic_bookmark,
        title = R.string.top_nav_saved,
        route = WatchLaterScreen::class
    ),
    INTERESTS(
        icon = R.drawable.ic_star,
        title = R.string.top_nav_interests,
        route = InterestsScreen::class
    )
}

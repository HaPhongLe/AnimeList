package com.example.animelist.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.animelist.R
import com.example.animelist.ui.feature.dashboard.navigation.DashboardGraph
import com.example.animelist.ui.feature.interests.navigation.InterestsScreen
import com.example.animelist.ui.feature.watchlater.navigation.WatchLaterScreenRoute
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
        route = DashboardGraph::class
    ),
    WATCH_LATER(
        icon = R.drawable.ic_bookmark,
        title = R.string.top_nav_saved,
        route = WatchLaterScreenRoute::class
    ),
    INTERESTS(
        icon = R.drawable.ic_star,
        title = R.string.top_nav_interests,
        route = InterestsScreen::class
    )
}

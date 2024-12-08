package com.example.animelist.ui.feature.watchlater.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.animelist.navigation.Route
import com.example.animelist.ui.feature.watchlater.WatchLaterScreen
import kotlinx.serialization.Serializable

@Serializable
object WatchLaterScreen : Route.Screen

fun NavHostController.navigateToWatchLater(navOptions: NavOptions) = navigate(route = WatchLaterScreen, navOptions = navOptions)

fun NavGraphBuilder.watchLaterScreen() {
    composable<WatchLaterScreen> { WatchLaterScreen() }
}

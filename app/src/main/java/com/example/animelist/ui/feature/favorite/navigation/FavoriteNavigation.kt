package com.example.animelist.ui.feature.favorite.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.animelist.navigation.Route
import com.example.animelist.ui.feature.favorite.FavoriteScreen
import kotlinx.serialization.Serializable

@Serializable
object FavoriteScreen : Route.Screen

fun NavHostController.navigateToFavorite(navOptions: NavOptions) = navigate(route = FavoriteScreen, navOptions = navOptions)

fun NavGraphBuilder.favouriteScreen() {
    composable<FavoriteScreen> { FavoriteScreen() }
}

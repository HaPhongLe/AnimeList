package com.example.animelist.ui.feature.favorite.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.animelist.navigation.Route
import com.example.animelist.ui.feature.detail.MediaDetailsScreen
import com.example.animelist.ui.feature.detail.navigation.MediaDetailsScreenRoute
import com.example.animelist.ui.feature.favorite.FavoriteScreen
import kotlinx.serialization.Serializable

@Serializable
object FavoriteGraph : Route.Graph

@Serializable
object FavoriteScreen : Route.Screen

fun NavHostController.navigateToFavorite(navOptions: NavOptions) = navigate(route = FavoriteGraph, navOptions = navOptions)

fun NavGraphBuilder.favouriteGraph(navHostController: NavHostController) {
    navigation<FavoriteGraph>(startDestination = FavoriteScreen) {
        composable<FavoriteScreen> { FavoriteScreen(navHostController) }
        composable<MediaDetailsScreenRoute> { MediaDetailsScreen() }
    }
}

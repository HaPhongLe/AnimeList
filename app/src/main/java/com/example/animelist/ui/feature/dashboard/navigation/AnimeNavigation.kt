package com.example.animelist.ui.feature.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.animelist.navigation.Route
import com.example.animelist.ui.feature.dashboard.AnimeScreen
import com.example.animelist.ui.feature.detail.MediaDetailsScreen
import com.example.animelist.ui.feature.detail.navigation.MediaDetailsScreenRoute
import kotlinx.serialization.Serializable

@Serializable
object AnimeGraph : Route.Graph

@Serializable
object AnimeScreen : Route.Screen

fun NavHostController.navigateToAnime(navOptions: NavOptions) = navigate(route = AnimeGraph, navOptions = navOptions)

fun NavGraphBuilder.animeGraph(navHostController: NavHostController) {
    navigation<AnimeGraph>(startDestination = AnimeScreen) {
        composable<AnimeScreen> { AnimeScreen(navHostController) }
        composable<MediaDetailsScreenRoute> { backStackEntry ->
            val animeDetailsScreen = backStackEntry.toRoute<MediaDetailsScreenRoute>()
            MediaDetailsScreen(animeId = animeDetailsScreen.id)
        }
    }
}

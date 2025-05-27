package com.example.animelist.ui.feature.manga.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.animelist.navigation.Route
import com.example.animelist.ui.feature.detail.MediaDetailsScreen
import com.example.animelist.ui.feature.detail.navigation.MediaDetailsScreenRoute
import com.example.animelist.ui.feature.manga.MangaScreen
import kotlinx.serialization.Serializable

@Serializable
object MangaGraph : Route.Graph

@Serializable
object MangaScreenRoute : Route.Screen

fun NavHostController.navigateToMangaScreen(navOptions: NavOptions) = navigate(route = MangaScreenRoute, navOptions = navOptions)

fun NavGraphBuilder.mangaScreen(navHostController: NavHostController) {
    navigation<MangaGraph>(startDestination = MangaScreenRoute) {
        composable<MangaScreenRoute> { MangaScreen(navHostController) }
        composable<MediaDetailsScreenRoute> { backStackEntry ->
            MediaDetailsScreen()
        }
    }
}

package com.example.animelist.ui.feature.manga.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.animelist.navigation.Route
import com.example.animelist.ui.feature.detail.AnimeDetailsScreen
import com.example.animelist.ui.feature.detail.navigation.AnimeDetailsScreenRoute
import com.example.animelist.ui.feature.manga.MangaScreen
import kotlinx.serialization.Serializable

@Serializable
object MangaScreenRoute : Route.Screen

fun NavHostController.navigateToMangaScreen(navOptions: NavOptions) = navigate(route = MangaScreenRoute, navOptions = navOptions)

fun NavGraphBuilder.mangaScreen(navHostController: NavHostController) {
    composable<MangaScreenRoute> { MangaScreen(navHostController) }
    composable<AnimeDetailsScreenRoute> { backStackEntry ->
        val animeDetailsScreen = backStackEntry.toRoute<AnimeDetailsScreenRoute>()
        AnimeDetailsScreen(animeId = animeDetailsScreen.id)
    }
}

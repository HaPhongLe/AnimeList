package com.example.animelist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.animelist.ui.AppState
import com.example.animelist.ui.feature.dashboard.navigation.AnimeGraph
import com.example.animelist.ui.feature.dashboard.navigation.animeGraph
import com.example.animelist.ui.feature.interests.navigation.favouriteScreen
import com.example.animelist.ui.feature.manga.navigation.mangaScreen

@Composable
fun MainNavHost(
    appState: AppState,
    modifier: Modifier = Modifier
) {
    val navHostController = appState.navController
    NavHost(
        navController = navHostController,
        startDestination = AnimeGraph,
        modifier = modifier
    ) {
        animeGraph(navHostController)
        mangaScreen(navHostController)
        favouriteScreen()
    }
}

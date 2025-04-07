package com.example.animelist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.animelist.ui.AppState
import com.example.animelist.ui.feature.dashboard.navigation.DashboardGraph
import com.example.animelist.ui.feature.dashboard.navigation.dashboardGraph
import com.example.animelist.ui.feature.interests.navigation.favouriteScreen
import com.example.animelist.ui.feature.watchlater.navigation.watchLaterScreen

@Composable
fun MainNavHost(
    appState: AppState,
    modifier: Modifier = Modifier
) {
    val navHostController = appState.navController
    NavHost(
        navController = navHostController,
        startDestination = DashboardGraph,
        modifier = modifier
    ) {
        dashboardGraph(navHostController)
        watchLaterScreen(navHostController)
        favouriteScreen()
    }
}

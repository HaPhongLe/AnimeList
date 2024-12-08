package com.example.animelist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.animelist.ui.AppState
import com.example.animelist.ui.feature.dashboard.navigation.DashboardScreen
import com.example.animelist.ui.feature.dashboard.navigation.dashboardScreen
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
        startDestination = DashboardScreen,
        modifier = modifier
    ) {
        dashboardScreen()
        watchLaterScreen()
        favouriteScreen()
    }
}

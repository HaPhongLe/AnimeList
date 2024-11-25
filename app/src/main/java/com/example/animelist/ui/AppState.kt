package com.example.animelist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.animelist.navigation.TopLevelNavDestination
import com.example.animelist.ui.feature.dashboard.navigation.navigateToDashboard
import com.example.animelist.ui.feature.interests.navigation.navigateToInterests
import com.example.animelist.ui.feature.watchlater.navigation.navigateToWatchLater


@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) = remember {
    AppState(navController)
}

class AppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelNavDestination: TopLevelNavDestination?
        @Composable get() = TopLevelNavDestination.entries.firstOrNull { topLevelNavDestination ->
            currentDestination?.hasRoute(route = topLevelNavDestination.route) == true
        }

    fun navigateToTopLevelNavDestination(topLevelNavDestination: TopLevelNavDestination){
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }

            launchSingleTop = true

            restoreState = true
        }

        when(topLevelNavDestination){
            TopLevelNavDestination.DASH_BOARD -> navController.navigateToDashboard(topLevelNavOptions)
            TopLevelNavDestination.WATCH_LATER -> navController.navigateToWatchLater(topLevelNavOptions)
            TopLevelNavDestination.INTERESTS -> navController.navigateToInterests(topLevelNavOptions)
        }
    }
}
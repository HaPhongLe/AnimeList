package com.example.animelist.ui.feature.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.animelist.navigation.Route
import com.example.animelist.ui.feature.dashboard.DashboardScreen
import com.example.animelist.ui.feature.detail.AnimeDetailsScreen
import com.example.animelist.ui.feature.detail.navigation.AnimeDetailsScreenRoute
import kotlinx.serialization.Serializable

@Serializable
object DashboardGraph : Route.Graph

@Serializable
object DashboardScreen : Route.Screen

fun NavHostController.navigateToDashboard(navOptions: NavOptions) = navigate(route = DashboardGraph, navOptions = navOptions)

fun NavGraphBuilder.dashboardGraph(navHostController: NavHostController) {
    navigation<DashboardGraph>(startDestination = DashboardScreen) {
        composable<DashboardScreen> { DashboardScreen(navHostController) }
        composable<AnimeDetailsScreenRoute> { backStackEntry ->
            val animeDetailsScreen = backStackEntry.toRoute<AnimeDetailsScreenRoute>()
            AnimeDetailsScreen(animeId = animeDetailsScreen.id)
        }
    }
}

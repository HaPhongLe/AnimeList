package com.example.animelist.ui.feature.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.animelist.navigation.Route
import com.example.animelist.ui.feature.dashboard.DashboardScreen
import kotlinx.serialization.Serializable


@Serializable
object DashboardScreen: Route.Screen

fun NavHostController.navigateToDashboard(navOptions: NavOptions) = navigate(route = DashboardScreen, navOptions = navOptions)

fun NavGraphBuilder.dashboardScreen(){
    composable<DashboardScreen> { DashboardScreen() }
}
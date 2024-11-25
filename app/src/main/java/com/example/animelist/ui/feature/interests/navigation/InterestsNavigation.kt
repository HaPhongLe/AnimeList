package com.example.animelist.ui.feature.interests.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.animelist.navigation.Route
import com.example.animelist.ui.feature.interests.InterestsScreen
import kotlinx.serialization.Serializable

@Serializable
object InterestsScreen: Route.Screen

fun NavHostController.navigateToInterests(navOptions: NavOptions) = navigate(route = InterestsScreen, navOptions = navOptions)

fun NavGraphBuilder.favouriteScreen(){
    composable<InterestsScreen> { InterestsScreen() }
}
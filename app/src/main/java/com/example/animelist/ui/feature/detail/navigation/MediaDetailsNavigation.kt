package com.example.animelist.ui.feature.detail.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.example.animelist.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data class MediaDetailsScreenRoute(val id: Int) : Route.Screen

fun NavHostController.navigateToMediaDetails(animeId: Int, navOptions: NavOptions? = null) = navigate(route = MediaDetailsScreenRoute(animeId), navOptions = navOptions)

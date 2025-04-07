package com.example.animelist.ui.feature.detail.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.example.animelist.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data class AnimeDetailsScreenRoute(val id: Int) : Route.Screen

fun NavHostController.navigateToAnimeDetails(animeId: Int, navOptions: NavOptions? = null) = navigate(route = AnimeDetailsScreenRoute(animeId), navOptions = navOptions)

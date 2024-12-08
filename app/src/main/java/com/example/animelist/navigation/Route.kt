package com.example.animelist.navigation

sealed interface Route {
    interface Graph : Route
    interface Screen : Route
}

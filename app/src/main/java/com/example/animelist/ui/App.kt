package com.example.animelist.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.animelist.navigation.MainNavHost
import com.example.animelist.ui.component.BottomNavBar


@Composable
fun App() {
    val appState = rememberAppState()
    Scaffold(
        bottomBar = {BottomNavBar(appState)}
    ) {contentPadding ->
        MainNavHost(
            modifier = Modifier.padding(contentPadding),
            appState = appState)
    }
}
package com.example.animelist.ui.feature.dashboard

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.animelist.ui.feature.dashboard.component.AnimeCard

@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val viewState by dashboardViewModel.viewState.collectAsStateWithLifecycle()

    DashboardScreen(viewState = viewState)
}

@Composable
private fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewState: DashboardViewModel.ViewState
) {
    if (viewState.isLoading.not()) {
        Row(
            modifier = modifier
                .horizontalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            viewState.trendingAnimeList.forEach {
                AnimeCard(it)
            }
        }
    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

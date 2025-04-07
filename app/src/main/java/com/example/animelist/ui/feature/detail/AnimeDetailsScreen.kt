package com.example.animelist.ui.feature.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AnimeDetailsScreen(
    animeId: Int,
    viewModel: AnimeDetailsViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(animeId) {
        viewModel.onResume(animeId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (viewState is AnimeDetailsViewModel.ViewState.Success) {
            (viewState as AnimeDetailsViewModel.ViewState.Success).animeDetails?.let {
                Text(modifier = Modifier.align(Alignment.Center), text = it.title ?: "No title")
            }
        }
    }
}

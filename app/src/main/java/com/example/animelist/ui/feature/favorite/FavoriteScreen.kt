package com.example.animelist.ui.feature.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.animelist.ui.component.MediaCard

@Composable
fun FavoriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    FavoriteScreen(viewState)
}

@Composable
private fun FavoriteScreen(
    viewState: FavouriteViewModel.ViewState,
    modifier: Modifier = Modifier
) {
    if (viewState.mediaList.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxSize()) {
            viewState.mediaList.forEach { media ->
                MediaCard(
                    media = media,
                    onClick = {}
                )
            }
        }
    }
}

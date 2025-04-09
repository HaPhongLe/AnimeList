package com.example.animelist.ui.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.AnimeDetails
import com.example.animelist.ui.feature.dashboard.component.FullScreenLoading
import com.example.animelist.ui.feature.detail.component.BasicInfo
import com.example.animelist.ui.feature.detail.component.FadedImage
import com.example.animelist.ui.feature.detail.component.StreamingEpisodes
import com.example.animelist.ui.theme.AnimeListTheme
import com.example.animelist.ui.theme.AppTheme

@Composable
fun AnimeDetailsScreen(
    animeId: Int,
    viewModel: AnimeDetailsViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(animeId) {
        viewModel.onResume(animeId)
    }

    when (viewState) {
        AnimeDetailsViewModel.ViewState.Loading -> FullScreenLoading()
        is AnimeDetailsViewModel.ViewState.Success -> AnimeDetailsSuccessScreen(animeDetails = (viewState as AnimeDetailsViewModel.ViewState.Success).animeDetails)
        is AnimeDetailsViewModel.ViewState.Error -> AnimeDetailsErrorScreen((viewState as AnimeDetailsViewModel.ViewState.Error).message)
    }
}

@Composable
private fun AnimeDetailsSuccessScreen(
    animeDetails: AnimeDetails,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        FadedImage(imageUrl = animeDetails.bannerImage)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Black)
                .padding(vertical = AppTheme.dimension.spaceL, horizontal = AppTheme.dimension.spaceS),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimension.spaceM),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            animeDetails.title?.let {
                Text(
                    text = it,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            BasicInfo(animeDetails = animeDetails)

            animeDetails.description?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = AppTheme.dimension.spaceM),
                    textAlign = TextAlign.Center,
                    text = it,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            StreamingEpisodes(streamingEpisode = animeDetails.streamingEpisodes)
        }
    }
}

@Composable
private fun AnimeDetailsErrorScreen(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = errorMessage, color = Color.Red)
    }
}

@Preview
@Composable
private fun AnimeDetailsSuccessScreen_Preview() {
    AnimeListTheme {
        AnimeDetailsSuccessScreen(animeDetails = AnimeDetails.mock())
    }
}

@Preview
@Composable
private fun AnimeDetailsErrorScreen_Preview() {
    AnimeListTheme {
        AnimeDetailsErrorScreen(errorMessage = "Error loading the anime details")
    }
}

package com.example.animelist.ui.feature.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.animelist.R
import com.example.animelist.ui.component.MediaCard
import com.example.animelist.ui.feature.detail.navigation.navigateToMediaDetails
import com.example.animelist.ui.theme.AppTheme

@Composable
fun FavoriteScreen(
    navHostController: NavHostController,
    viewModel: FavouriteViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is FavouriteViewModel.Event.NavigatingToDetailScreen -> navHostController.navigateToMediaDetails(event.id)
            }
        }
    }

    FavoriteScreen(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.05f)),
        viewState = viewState,
        onMediaClick = viewModel::onMediaClick
    )
}

@Composable
private fun FavoriteScreen(
    viewState: FavouriteViewModel.ViewState,
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (viewState.mediaList.isNotEmpty()) {
        Column(modifier = modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimension.spaceM),
                text = stringResource(R.string.favourite).uppercase(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Column(modifier = Modifier.padding(16.dp)) {
                viewState.mediaList.forEachIndexed() { index, media ->
                    if (index != 0) {
                        Spacer(modifier = Modifier.height(AppTheme.dimension.spaceM))
                    }
                    MediaCard(
                        media = media,
                        onClick = onMediaClick
                    )
                }
            }
        }
    }
}

package com.example.animelist.ui.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.animelist.R
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.repository.SortType
import com.example.animelist.ui.component.ErrorDialog
import com.example.animelist.ui.component.FullScreenLoading
import com.example.animelist.ui.component.PullToRefreshMediaList
import com.example.animelist.ui.component.SortTypeDropDownMenu
import com.example.animelist.ui.feature.detail.navigation.navigateToMediaDetails
import com.example.animelist.ui.theme.AppTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun AnimeScreen(
    navHostController: NavHostController,
    viewModel: AnimeViewModel = hiltViewModel()
) {
    val animeLazyPagingItems = viewModel.mediaState.collectAsLazyPagingItems()
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    var errorRefreshingDialog by remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(Unit) {
        viewModel.setOnRefresh(animeLazyPagingItems::refresh)
        viewModel.eventFlow.collect { event ->
            when (event) {
                is AnimeViewModel.Event.RefreshError -> errorRefreshingDialog = event.message
                is AnimeViewModel.Event.NavigateToDetailsScreen -> navHostController.navigateToMediaDetails(animeId = event.animeId)
            }
        }
    }

    LaunchedEffect(animeLazyPagingItems.loadState) {
        viewModel.onLoadStateReceived(animeLazyPagingItems.loadState)
    }

    errorRefreshingDialog?.let {
        ErrorDialog(
            message = it,
            onRetry = {
                animeLazyPagingItems.retry()
                errorRefreshingDialog = null
            },
            onDismiss = { errorRefreshingDialog = null }
        )
    }

    AnimeScreen(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.05f)),
        viewState = viewState,
        lazyPagingItems = animeLazyPagingItems,
        onRefresh = { viewModel.onRefresh.invoke() },
        onAnimeClick = viewModel::onAnimeClick,
        onSortTypeClick = viewModel::onSortTypeClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimeScreen(
    modifier: Modifier = Modifier,
    viewState: AnimeViewModel.ViewState,
    lazyPagingItems: LazyPagingItems<Media>,
    onRefresh: () -> Unit,
    onAnimeClick: (Int) -> Unit,
    onSortTypeClick: (SortType) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.dimension.spaceM),
            text = stringResource(R.string.anime_title).uppercase(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        SortTypeDropDownMenu(
            modifier = Modifier.padding(AppTheme.dimension.spaceL),
            options = SortType.entries,
            selectedOption = viewState.selectedSortType,
            onOptionClick = onSortTypeClick
        )
        if (viewState.isLoading) {
            FullScreenLoading()
        } else {
            PullToRefreshMediaList(
                isRefreshing = viewState.isRefreshing,
                isAppending = viewState.isAppendingLoading,
                appendingError = viewState.appendError,
                onRefresh = onRefresh,
                lazyPagingItems = lazyPagingItems,
                onAnimeClick = onAnimeClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AnimeScreen_Success_Preview() {
    val mockData = mutableListOf<Media>()
    (1..10).forEach { _ ->
        mockData.add(Media.mock())
    }
    val lazyPagingItems = flowOf(PagingData.from(mockData)).collectAsLazyPagingItems()

    AnimeScreen(
        viewState = AnimeViewModel.ViewState(isLoading = false),
        lazyPagingItems = lazyPagingItems,
        onRefresh = {},
        onAnimeClick = {},
        onSortTypeClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun AnimeScreen_Error_Preview() {
    val lazyPagingItems = flowOf(PagingData.from(emptyList<Media>())).collectAsLazyPagingItems()
    AnimeScreen(
        viewState = AnimeViewModel.ViewState(isLoading = false, appendError = "Error load data"),
        lazyPagingItems = lazyPagingItems,
        onRefresh = {},
        onAnimeClick = {},
        onSortTypeClick = {}
    )
}

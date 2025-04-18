package com.example.animelist.ui.feature.manga

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
import com.example.animelist.domain.model.Manga
import com.example.animelist.domain.repository.SortType
import com.example.animelist.ui.component.ErrorDialog
import com.example.animelist.ui.component.FullScreenLoading
import com.example.animelist.ui.component.SortTypeDropDownMenu
import com.example.animelist.ui.feature.detail.navigation.navigateToAnimeDetails
import com.example.animelist.ui.feature.manga.component.PullToRefreshMangaList
import com.example.animelist.ui.theme.AppTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun MangaScreen(
    navHostController: NavHostController,
    viewModel: MangaViewModel = hiltViewModel()
) {
    val animeLazyPagingItems = viewModel.animeState.collectAsLazyPagingItems()
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    var errorRefreshingDialog by remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(Unit) {
        viewModel.setOnRefresh(animeLazyPagingItems::refresh)
        viewModel.eventFlow.collect { event ->
            when (event) {
                is MangaViewModel.Event.RefreshError -> errorRefreshingDialog = event.message
                is MangaViewModel.Event.NavigateToDetailsScreen -> navHostController.navigateToAnimeDetails(animeId = event.animeId)
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

    MangaScreen(
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
private fun MangaScreen(
    modifier: Modifier = Modifier,
    viewState: MangaViewModel.ViewState,
    lazyPagingItems: LazyPagingItems<Manga>,
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
            text = stringResource(R.string.manga_title).uppercase(),
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
            PullToRefreshMangaList(
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
private fun MangaScreen_Success_Preview() {
    val mockData = mutableListOf<Manga>()
    (1..10).forEach { _ ->
        mockData.add(Manga.mock())
    }
    val lazyPagingItems = flowOf(PagingData.from(mockData)).collectAsLazyPagingItems()

    MangaScreen(
        viewState = MangaViewModel.ViewState(isLoading = false),
        lazyPagingItems = lazyPagingItems,
        onRefresh = {},
        onAnimeClick = {},
        onSortTypeClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreen_Error_Preview() {
    val lazyPagingItems = flowOf(PagingData.from(emptyList<Manga>())).collectAsLazyPagingItems()
    MangaScreen(
        viewState = MangaViewModel.ViewState(isLoading = false, appendError = "Error load data"),
        lazyPagingItems = lazyPagingItems,
        onRefresh = {},
        onAnimeClick = {},
        onSortTypeClick = {}
    )
}

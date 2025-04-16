package com.example.animelist.ui.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.animelist.R
import com.example.animelist.domain.mockModel.mock
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.repository.SortType
import com.example.animelist.ui.component.ErrorDialog
import com.example.animelist.ui.component.FullScreenLoading
import com.example.animelist.ui.component.SortTypeDropDownMenu
import com.example.animelist.ui.feature.dashboard.component.AnimeCard
import com.example.animelist.ui.feature.detail.navigation.navigateToAnimeDetails
import com.example.animelist.ui.theme.AppTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun DashboardScreen(
    navHostController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel()
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
                is DashboardViewModel.Event.RefreshError -> errorRefreshingDialog = event.message
                is DashboardViewModel.Event.NavigateToDetailsScreen -> navHostController.navigateToAnimeDetails(animeId = event.animeId)
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

    DashboardScreen(
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
private fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewState: DashboardViewModel.ViewState,
    lazyPagingItems: LazyPagingItems<Anime>,
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
            PullToRefreshAnimeList(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PullToRefreshAnimeList(
    isRefreshing: Boolean,
    appendingError: String?,
    isAppending: Boolean,
    onRefresh: () -> Unit,
    lazyPagingItems: LazyPagingItems<Anime>,
    modifier: Modifier = Modifier,
    onAnimeClick: (Int) -> Unit
) {
    val state = rememberPullToRefreshState()
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier.fillMaxSize(),
        state = state,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                state = state
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            lazyPagingItems.apply {
                items(itemCount) { index ->
                    if (index != 0) {
                        Spacer(modifier = Modifier.height(AppTheme.dimension.spaceM))
                    }
                    AnimeCard(
                        anime = get(index)!!,
                        ranking = index + 1,
                        onClick = onAnimeClick
                    )
                }

                if (isAppending) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(AppTheme.dimension.spaceS)) {
                            CircularProgressIndicator()
                        }
                    }
                }

                appendingError?.let {
                    item {
                        ErrorMessage(
                            modifier = Modifier.fillParentMaxSize(),
                            message = it,
                            onClickRetry = ::retry
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorMessage(
    modifier: Modifier = Modifier,
    message: String = "Error loading!",
    onClickRetry: () -> Unit = {}
) {
    Text(modifier = modifier.clickable { onClickRetry.invoke() }, text = message)
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreen_Success() {
    val mockData = mutableListOf<Anime>()
    (1..10).forEach { _ ->
        mockData.add(Anime.mock())
    }
    val lazyPagingItems = flowOf(PagingData.from(mockData)).collectAsLazyPagingItems()

    DashboardScreen(
        viewState = DashboardViewModel.ViewState(isLoading = false),
        lazyPagingItems = lazyPagingItems,
        onRefresh = {},
        onAnimeClick = {},
        onSortTypeClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreen_Error() {
    val lazyPagingItems = flowOf(PagingData.from(emptyList<Anime>())).collectAsLazyPagingItems()
    DashboardScreen(
        viewState = DashboardViewModel.ViewState(isLoading = false, appendError = "Error load data"),
        lazyPagingItems = lazyPagingItems,
        onRefresh = {},
        onAnimeClick = {},
        onSortTypeClick = {}
    )
}

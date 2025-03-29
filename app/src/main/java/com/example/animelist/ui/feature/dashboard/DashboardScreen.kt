package com.example.animelist.ui.feature.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.animelist.domain.model.Anime
import com.example.animelist.ui.component.ErrorDialog
import com.example.animelist.ui.component.FullScreenLoading
import com.example.animelist.ui.feature.dashboard.component.AnimeCard

@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val animeLazyPagingItems = dashboardViewModel.animeState.collectAsLazyPagingItems()
    val viewState by dashboardViewModel.viewState.collectAsStateWithLifecycle()
    var errorRefreshingDialog by remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(Unit) {
        dashboardViewModel.setOnRefresh(animeLazyPagingItems::refresh)
        dashboardViewModel.eventFlow.collect { event ->
            when (event) {
                is DashboardViewModel.Event.RefreshError -> errorRefreshingDialog = event.message
            }
        }
    }

    LaunchedEffect(animeLazyPagingItems.loadState) {
        dashboardViewModel.onLoadStateReceived(animeLazyPagingItems.loadState)
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
        viewState = viewState,
        lazyPagingItems = animeLazyPagingItems,
        onRefresh = { dashboardViewModel.onRefresh.invoke() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewState: DashboardViewModel.ViewState,
    lazyPagingItems: LazyPagingItems<Anime>,
    onRefresh: () -> Unit
) {
    if (viewState.isLoading) {
        FullScreenLoading()
    } else {
        PullToRefreshAnimeList(
            isRefreshing = viewState.isRefreshing,
            isAppending = viewState.isAppendingLoading,
            appendingError = viewState.appendError,
            onRefresh = onRefresh,
            lazyPagingItems = lazyPagingItems,
            modifier = modifier
        )
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
    modifier: Modifier = Modifier
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
                    AnimeCard(
                        anime = get(index)!!,
                        ranking = index + 1
                    )
                }

                if (isAppending) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
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

package com.example.animelist.ui.feature.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.animelist.domain.model.Anime
import com.example.animelist.ui.feature.dashboard.component.AnimeCard

@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val viewState = dashboardViewModel.animeState.collectAsLazyPagingItems()

    DashboardScreen(lazyPagingItems = viewState)
}

@Composable
private fun DashboardScreen(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<Anime>
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        lazyPagingItems.apply {
            items(itemCount) { index ->
                AnimeCard(get(index)!!)
            }
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier.fillParentMaxSize(),
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                loadState.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier,
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() }
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

package com.example.animelist.ui.feature.dashboard.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.animelist.domain.model.Anime
import com.example.animelist.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshAnimeList(
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
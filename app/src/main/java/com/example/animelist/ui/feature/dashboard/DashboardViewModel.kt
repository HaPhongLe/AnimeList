package com.example.animelist.ui.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.animelist.R
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.repository.AnimeRepository
import com.example.animelist.ui.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _animeState = MutableStateFlow<PagingData<Anime>>(PagingData.empty())
    val animeState: StateFlow<PagingData<Anime>> get() = _animeState

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow

    var onRefresh: () -> Unit = {}
        private set

    init {
        viewModelScope.launch {
            animeRepository.getTrendingAnime().distinctUntilChanged().cachedIn(viewModelScope).collect { pagingData ->
                _animeState.update { pagingData }
                _viewState.update { oldState -> oldState.copy(isLoading = false) }
            }
        }
    }

    fun setOnRefresh(onRefreshFunction: () -> Unit) {
        onRefresh = onRefreshFunction
    }

    fun onLoadStateReceived(loadStates: CombinedLoadStates) {
        val isRefreshing = loadStates.refresh is LoadState.Loading
        val isAppending = loadStates.append is LoadState.Loading
        val refreshingError = loadStates.refresh as? LoadState.Error
        val appendingError = loadStates.append as? LoadState.Error
        _viewState.update { oldState ->
            oldState.copy(
                isRefreshing = isRefreshing,
                isAppendingLoading = isAppending,
                appendError = if (appendingError != null) (loadStates.append as LoadState.Error).error.localizedMessage else null
            )
        }
        refreshingError?.let {
            emitEvent(
                Event.RefreshError(
                    message = (loadStates.refresh as LoadState.Error).error.localizedMessage ?: resourceProvider.stringForRes(
                        R.string.error_refreshing
                    )
                )
            )
        }
    }

    fun onAnimeClick(id: Int) {
        emitEvent(Event.NavigateToDetailsScreen(id))
    }

    private fun emitEvent(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    data class ViewState(
        val isAppendingLoading: Boolean = false,
        val appendError: String? = null,
        val isRefreshing: Boolean = false,
        val isLoading: Boolean = true
    )

    sealed interface Event {
        data class RefreshError(val message: String) : Event
        data class NavigateToDetailsScreen(val animeId: Int) : Event
    }
}

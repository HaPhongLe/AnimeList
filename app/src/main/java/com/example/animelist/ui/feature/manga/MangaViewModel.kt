package com.example.animelist.ui.feature.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.animelist.R
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.repository.MediaRepository
import com.example.animelist.domain.repository.MediaType
import com.example.animelist.domain.repository.SortType
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
class MangaViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _mediaState = MutableStateFlow<PagingData<Media>>(PagingData.empty())
    val mediaState: StateFlow<PagingData<Media>> get() = _mediaState

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow

    var onRefresh: () -> Unit = {}
        private set

    init {
        fetchData(viewState.value.selectedSortType)
    }

    private fun fetchData(sortType: SortType) {
        viewModelScope.launch {
            mediaRepository.getTopMedia(type = MediaType.Manga, sortType = sortType).distinctUntilChanged().cachedIn(viewModelScope).collect { pagingData ->
                _mediaState.update { pagingData }
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

    fun onSortTypeClick(sortType: SortType) {
        _viewState.update { it.copy(selectedSortType = sortType) }
        fetchData(sortType)
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
        val isLoading: Boolean = true,
        val selectedSortType: SortType = SortType.Trending
    )

    sealed interface Event {
        data class RefreshError(val message: String) : Event
        data class NavigateToDetailsScreen(val animeId: Int) : Event
    }
}

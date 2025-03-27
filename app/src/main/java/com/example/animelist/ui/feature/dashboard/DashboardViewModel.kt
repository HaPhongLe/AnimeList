package com.example.animelist.ui.feature.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    animeRepository: AnimeRepository
) : ViewModel() {

    private val _animeState = MutableStateFlow<PagingData<Anime>>(PagingData.empty())
    val animeState: StateFlow<PagingData<Anime>> get() = _animeState

    init {
        viewModelScope.launch {
            animeRepository.getTrendingAnime().distinctUntilChanged().cachedIn(viewModelScope).collect { pagingData ->
                Log.d("DashboardViewModel", "animeList: $pagingData")
                _animeState.update { pagingData }
            }
        }
    }

    data class ViewState(
        val trendingAnimeList: List<Anime> = emptyList(),
        val isLoading: Boolean = true
    )
}

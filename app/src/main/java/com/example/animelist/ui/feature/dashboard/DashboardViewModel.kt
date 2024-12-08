package com.example.animelist.ui.feature.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.domain.AnimeClient
import com.example.animelist.domain.model.Anime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    animeClient: AnimeClient
) : ViewModel() {

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> get() = _viewState

    init {
        viewModelScope.launch {
            val animeList = animeClient.getTopTrendingAnime()
            Log.d("DashboardViewModel", "animeList: $animeList")
            _viewState.update { it.copy(trendingAnimeList = animeList, isLoading = false) }
        }
    }

    data class ViewState(
        val trendingAnimeList: List<Anime> = emptyList(),
        val isLoading: Boolean = true
    )
}

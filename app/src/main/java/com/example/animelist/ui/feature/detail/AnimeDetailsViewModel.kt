package com.example.animelist.ui.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.domain.model.AnimeDetails
import com.example.animelist.domain.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {
    private var currentAnimeId: Int? = null
    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val viewState: StateFlow<ViewState> = _viewState

    fun onResume(animeId: Int) {
        if (animeId != currentAnimeId) {
            viewModelScope.launch {
                try {
                    val animeDetails = animeRepository.getAnimeById(animeId)
                    animeDetails?.let { details ->
                        _viewState.update { ViewState.Success(details) }
                        currentAnimeId = animeId
                    }
                } catch (e: Exception) {
                    _viewState.update { ViewState.Error(e.message ?: "Unknown Error") }
                }
            }
        }
    }

    sealed interface ViewState {
        data object Loading : ViewState
        data class Error(val message: String) : ViewState
        data class Success(val animeDetails: AnimeDetails) : ViewState
    }
}

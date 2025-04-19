package com.example.animelist.ui.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.domain.model.MediaDetails
import com.example.animelist.domain.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaDetailsViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {
    private var currentAnimeId: Int? = null
    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val viewState: StateFlow<ViewState> = _viewState

    fun onResume(animeId: Int) {
        if (animeId != currentAnimeId) {
            viewModelScope.launch {
                try {
                    val animeDetails = mediaRepository.getMediaById(animeId)
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
        data class Success(val mediaDetails: MediaDetails) : ViewState
    }
}

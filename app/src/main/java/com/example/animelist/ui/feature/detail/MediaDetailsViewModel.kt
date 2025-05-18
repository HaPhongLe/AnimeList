package com.example.animelist.ui.feature.detail

import androidx.lifecycle.SavedStateHandle
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
    private val mediaRepository: MediaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var currentAnimeId: Int? = null
    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val viewState: StateFlow<ViewState> = _viewState
    private val animeId: Int = requireNotNull(savedStateHandle["id"])
    init {
        viewModelScope.launch {
            mediaRepository.getNumberOfSavedMediaByIdFlow(animeId).collect { numberOfSavedMediaById ->
                try {
                    val animeDetails = mediaRepository.getMediaDetailsById(animeId)
                    val isBookmarked = numberOfSavedMediaById > 0
                    animeDetails?.let { details ->
                        _viewState.update { ViewState.Success(details, isBookmarked) }
                        currentAnimeId = animeId
                    }
                } catch (e: Exception) {
                    _viewState.update { ViewState.Error(e.message ?: "Unknown Error") }
                }
            }
        }
    }

    fun onBookmarkClick() {
        viewModelScope.launch {
            if (viewState.value is ViewState.Success) {
                if ((viewState.value as ViewState.Success).isBookmarked) {
                    mediaRepository.deleteSavedMediaId((viewState.value as ViewState.Success).mediaDetails.id)
                } else {
                    mediaRepository.saveMediaId(animeId)
                }
            }
        }
    }

    sealed interface ViewState {
        data object Loading : ViewState
        data class Error(val message: String) : ViewState
        data class Success(val mediaDetails: MediaDetails, val isBookmarked: Boolean) : ViewState
    }
}

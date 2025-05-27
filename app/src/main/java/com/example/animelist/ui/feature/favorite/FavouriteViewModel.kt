package com.example.animelist.ui.feature.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(mediaRepository: MediaRepository) : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState

    init {
        viewModelScope.launch(context = SupervisorJob()) {
            mediaRepository.getSavedMediaIds().collect { idList ->
                val mediaList = mutableListOf<Media>()
                try {
                    idList.forEach { id ->
                        val media = async(context = Dispatchers.IO) { mediaRepository.getMediaById(id) }
                        media.await()?.let { mediaList.add(it) }
                    }
                    _viewState.update { oldState -> ViewState(isLoading = false, mediaList = mediaList, errorMessage = null) }
                } catch (e: Exception) {
                    _viewState.update { oldState -> ViewState(isLoading = false, mediaList = mediaList, errorMessage = e.localizedMessage) }
                }
            }
        }
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val mediaList: List<Media> = emptyList(),
        val errorMessage: String? = null
    )
}

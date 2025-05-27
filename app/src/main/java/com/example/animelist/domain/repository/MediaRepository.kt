package com.example.animelist.domain.repository

import androidx.paging.PagingData
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.model.MediaDetails
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun getTopMedia(type: MediaType, sortType: SortType): Flow<PagingData<Media>>
    suspend fun getMediaDetailsById(id: Int): MediaDetails?
    suspend fun getMediaById(id: Int): Media?
    suspend fun saveMediaId(id: Int)
    suspend fun deleteSavedMediaId(id: Int)
    fun getNumberOfSavedMediaByIdFlow(id: Int): Flow<Int>
    fun getSavedMediaIds(): Flow<List<Int>>
}

enum class SortType {
    Trending,
    Popular
}

enum class MediaType {
    Anime,
    Manga
}

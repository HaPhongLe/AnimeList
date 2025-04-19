package com.example.animelist.domain.repository

import androidx.paging.PagingData
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.model.MediaDetails
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun getTopMedia(type: MediaType, sortType: SortType): Flow<PagingData<Media>>
    suspend fun getMediaById(id: Int): MediaDetails?
}

enum class SortType {
    Trending,
    Popular
}

enum class MediaType {
    Anime,
    Manga
}

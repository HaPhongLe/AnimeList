package com.example.animelist.domain.repository

import androidx.paging.PagingData
import com.example.animelist.domain.model.Manga
import com.example.animelist.domain.model.MediaDetails
import kotlinx.coroutines.flow.Flow

interface MangaRepository {
    fun getTopManga(mediaType: MediaType, sortType: SortType): Flow<PagingData<Manga>>
    suspend fun getAnimeById(id: Int): MediaDetails?
}
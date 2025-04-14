package com.example.animelist.domain.repository

import androidx.paging.PagingData
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.model.AnimeDetails
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getTopAnime(sortType: SortType): Flow<PagingData<Anime>>
    suspend fun getAnimeById(id: Int): AnimeDetails?
}

enum class SortType {
    Trending,
    Popular
}

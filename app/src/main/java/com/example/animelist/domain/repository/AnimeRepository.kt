package com.example.animelist.domain.repository

import androidx.paging.PagingData
import com.example.animelist.domain.model.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getTrendingAnime(): Flow<PagingData<Anime>>
}

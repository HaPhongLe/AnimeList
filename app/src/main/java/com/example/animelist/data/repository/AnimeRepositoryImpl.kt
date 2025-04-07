package com.example.animelist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.animelist.data.AnimePagingSource
import com.example.animelist.data.api.AnimeApi
import com.example.animelist.domain.ANIME_PER_PAGE
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.model.AnimeDetails
import com.example.animelist.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(private val animeApi: AnimeApi) : AnimeRepository {
    override fun getTrendingAnime(): Flow<PagingData<Anime>> = Pager(
        config = PagingConfig(pageSize = ANIME_PER_PAGE),
        pagingSourceFactory = { AnimePagingSource(animeApi = animeApi) }
    ).flow

    override suspend fun getAnimeById(id: Int): AnimeDetails? =
        animeApi.getAnimeDetailsById(id)
}

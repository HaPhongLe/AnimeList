package com.example.animelist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.animelist.data.AnimePagingSource
import com.example.animelist.data.MangaPagingSource
import com.example.animelist.data.api.AnimeApi
import com.example.animelist.domain.MEDIA_PER_PAGE
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.model.Manga
import com.example.animelist.domain.model.MediaDetails
import com.example.animelist.domain.repository.MediaRepository
import com.example.animelist.domain.repository.SortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(private val animeApi: AnimeApi) : MediaRepository {
    override fun getTopAnime(sortType: SortType): Flow<PagingData<Anime>> = Pager(
        config = PagingConfig(pageSize = MEDIA_PER_PAGE),
        pagingSourceFactory = { AnimePagingSource(animeApi = animeApi, sortType = sortType) }
    ).flow

    override fun getTopManga(sortType: SortType): Flow<PagingData<Manga>> = Pager(
        config = PagingConfig(pageSize = MEDIA_PER_PAGE),
        pagingSourceFactory = { MangaPagingSource(animeApi = animeApi, sortType = sortType) }
    ).flow

    override suspend fun getMediaById(id: Int): MediaDetails? =
        animeApi.getMediaDetailsById(id)
}

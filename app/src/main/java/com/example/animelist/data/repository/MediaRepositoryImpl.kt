package com.example.animelist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.animelist.data.MediaPagingSource
import com.example.animelist.data.api.MediaApi
import com.example.animelist.domain.MEDIA_PER_PAGE
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.model.MediaDetails
import com.example.animelist.domain.repository.MediaRepository
import com.example.animelist.domain.repository.MediaType
import com.example.animelist.domain.repository.SortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(private val mediaApi: MediaApi) : MediaRepository {
    override fun getTopMedia(type: MediaType, sortType: SortType): Flow<PagingData<Media>> = Pager(
        config = PagingConfig(pageSize = MEDIA_PER_PAGE),
        pagingSourceFactory = { MediaPagingSource(mediaApi = mediaApi, type = type, sortType = sortType) }
    ).flow

    override suspend fun getMediaById(id: Int): MediaDetails? =
        mediaApi.getMediaDetailsById(id)
}

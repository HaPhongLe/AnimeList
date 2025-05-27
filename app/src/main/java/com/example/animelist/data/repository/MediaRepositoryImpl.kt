package com.example.animelist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.animelist.data.MediaPagingSource
import com.example.animelist.data.api.MediaApi
import com.example.animelist.data.local.media.MediaDao
import com.example.animelist.data.local.media.MediaEntity
import com.example.animelist.domain.MEDIA_PER_PAGE
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.model.MediaDetails
import com.example.animelist.domain.repository.MediaRepository
import com.example.animelist.domain.repository.MediaType
import com.example.animelist.domain.repository.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(private val mediaApi: MediaApi, private val mediaDao: MediaDao) : MediaRepository {
    override fun getTopMedia(type: MediaType, sortType: SortType): Flow<PagingData<Media>> = Pager(
        config = PagingConfig(pageSize = MEDIA_PER_PAGE),
        pagingSourceFactory = { MediaPagingSource(mediaApi = mediaApi, type = type, sortType = sortType) }
    ).flow

    override suspend fun getMediaDetailsById(id: Int): MediaDetails? =
        mediaApi.getMediaDetailsById(id)

    override suspend fun getMediaById(id: Int): Media? = mediaApi.getMediaById(id)

    override suspend fun saveMediaId(id: Int) {
        mediaDao.insert(MediaEntity(id = id))
    }

    override suspend fun deleteSavedMediaId(id: Int) {
        mediaDao.delete(MediaEntity(id = id))
    }

    override fun getNumberOfSavedMediaByIdFlow(id: Int): Flow<Int> = mediaDao.countMediaEntityById(id)

    override fun getSavedMediaIds(): Flow<List<Int>> = mediaDao.getAll().map { value: List<MediaEntity> -> value.map { mediaEntity -> mediaEntity.id } }
}

package com.example.animelist.data.repository

import com.example.animelist.data.local.media.MediaDao
import com.example.animelist.data.local.media.MediaEntity
import com.example.animelist.data.mapper.toDomain
import com.example.animelist.data.mapper.toEntity
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.repository.MediaLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MediaLocalRepositoryImpl @Inject constructor(private val mediaDao: MediaDao) : MediaLocalRepository {
    override fun getSavedMedia(): Flow<List<Media>> = mediaDao.getAll().map { value: List<MediaEntity> -> value.map { it.toDomain() } }

    override fun saveMedia(media: Media) = mediaDao.insert(media.toEntity())

    override fun deleteMedia(media: Media) = mediaDao.delete(media.toEntity())
}

package com.example.animelist.domain.repository

import com.example.animelist.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaLocalRepository {
    fun getSavedMedia(): Flow<List<Media>>
    fun saveMedia(media: Media)
    fun deleteMedia(media: Media)
}

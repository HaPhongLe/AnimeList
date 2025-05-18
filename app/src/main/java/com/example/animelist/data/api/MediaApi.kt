package com.example.animelist.data.api

import com.example.animelist.domain.MEDIA_PER_PAGE
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.model.MediaDetails
import com.example.animelist.domain.repository.MediaType
import com.example.animelist.domain.repository.SortType

interface MediaApi {
    suspend fun getTopMedia(page: Int = 1, perPage: Int = MEDIA_PER_PAGE, type: MediaType, sortType: SortType): List<Media>
    suspend fun getMediaDetailsById(id: Int): MediaDetails?
    suspend fun getMediaById(id: Int): Media?
}

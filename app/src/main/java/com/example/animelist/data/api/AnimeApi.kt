package com.example.animelist.data.api

import com.example.animelist.domain.MEDIA_PER_PAGE
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.model.AnimeDetails
import com.example.animelist.domain.repository.SortType

interface AnimeApi {
    suspend fun getTopAnime(page: Int = 1, perPage: Int = MEDIA_PER_PAGE, sortType: SortType): List<Anime>
    suspend fun getAnimeDetailsById(id: Int): AnimeDetails?
}

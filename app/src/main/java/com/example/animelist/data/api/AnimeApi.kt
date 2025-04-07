package com.example.animelist.data.api

import com.example.animelist.domain.ANIME_PER_PAGE
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.model.AnimeDetails

interface AnimeApi {
    suspend fun getTopTrendingAnime(page: Int = 1, perPage: Int = ANIME_PER_PAGE): List<Anime>
    suspend fun getAnimeDetailsById(id: Int): AnimeDetails?
}

package com.example.animelist.domain

import com.example.animelist.domain.model.Anime

interface AnimeClient {
    suspend fun getTopTrendingAnime(): List<Anime>
}

package com.example.animelist.data

import com.apollographql.apollo3.ApolloClient
import com.example.TrendingAnimeQuery
import com.example.animelist.data.mapper.toAnime
import com.example.animelist.domain.AnimeClient
import com.example.animelist.domain.model.Anime
import com.example.type.MediaSort
import com.example.type.MediaType

class ApolloAnimeClient(
    private val apolloClient: ApolloClient
) : AnimeClient {
    override suspend fun getTopTrendingAnime(): List<Anime> {
        return apolloClient
            .query(query = TrendingAnimeQuery(page = 1, perPage = 10, sort = listOf(MediaSort.TRENDING_DESC), type = MediaType.ANIME))
            .execute()
            .data
            ?.Page
            ?.media
            ?.filterNotNull()
            ?.map { medium: TrendingAnimeQuery.Medium -> medium.toAnime() }
            ?: emptyList()
    }
}

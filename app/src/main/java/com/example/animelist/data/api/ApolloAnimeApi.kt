package com.example.animelist.data.api

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.AnimeByIdQuery
import com.example.TrendingAnimeQuery
import com.example.animelist.data.mapper.toAnime
import com.example.animelist.data.mapper.toAnimeDetails
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.model.AnimeDetails
import com.example.type.MediaSort
import com.example.type.MediaType

class ApolloAnimeApi(
    private val apolloClient: ApolloClient
) : AnimeApi {
    override suspend fun getTopTrendingAnime(page: Int, perPage: Int): List<Anime> {
        return apolloClient
            .query(query = TrendingAnimeQuery(page = page, perPage = perPage, sort = listOf(MediaSort.TRENDING_DESC), type = MediaType.ANIME))
            .execute()
            .data
            ?.Page
            ?.media
            ?.filterNotNull()
            ?.map { medium: TrendingAnimeQuery.Medium -> medium.toAnime() }
            ?: emptyList()
    }

    override suspend fun getAnimeDetailsById(id: Int): AnimeDetails? =
        try {
            apolloClient
                .query(query = AnimeByIdQuery(mediaId = Optional.present(id)))
                .execute()
                .data
                ?.Media?.toAnimeDetails()
        } catch (e: Exception) {
            throw e
        }
}

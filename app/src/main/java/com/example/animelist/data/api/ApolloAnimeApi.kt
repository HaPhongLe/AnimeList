package com.example.animelist.data.api

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.GetAnimeBySortTypeQuery
import com.example.GetMangaBySortTypeQuery
import com.example.MediaByIdQuery
import com.example.animelist.data.mapper.toAnime
import com.example.animelist.data.mapper.toAnimeDetails
import com.example.animelist.data.mapper.toManga
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.model.Manga
import com.example.animelist.domain.model.MediaDetails
import com.example.animelist.domain.repository.SortType
import com.example.type.MediaSort
import com.example.type.MediaType

class ApolloAnimeApi(
    private val apolloClient: ApolloClient
) : AnimeApi {
    override suspend fun getTopAnime(page: Int, perPage: Int, sortType: SortType): List<Anime> {
        val mediaSort = when (sortType) {
            SortType.Popular -> MediaSort.POPULARITY_DESC
            SortType.Trending -> MediaSort.TRENDING_DESC
        }
        return apolloClient
            .query(query = GetAnimeBySortTypeQuery(page = page, perPage = perPage, sort = listOf(mediaSort), type = MediaType.ANIME))
            .execute()
            .data
            ?.Page
            ?.media
            ?.filterNotNull()
            ?.map { medium: GetAnimeBySortTypeQuery.Medium -> medium.toAnime() }
            ?: emptyList()
    }

    override suspend fun getTopManga(page: Int, perPage: Int, sortType: SortType): List<Manga> {
        val mediaSort = when (sortType) {
            SortType.Popular -> MediaSort.POPULARITY_DESC
            SortType.Trending -> MediaSort.TRENDING_DESC
        }
        return apolloClient
            .query(query = GetMangaBySortTypeQuery(page = page, perPage = perPage, sort = listOf(mediaSort), type = MediaType.MANGA))
            .execute()
            .data
            ?.Page
            ?.media
            ?.filterNotNull()
            ?.map { medium: GetMangaBySortTypeQuery.Medium -> medium.toManga() }
            ?: emptyList()
    }

    override suspend fun getMediaDetailsById(id: Int): MediaDetails? =
        try {
            apolloClient
                .query(query = MediaByIdQuery(mediaId = Optional.present(id)))
                .execute()
                .data
                ?.Media?.toAnimeDetails()
        } catch (e: Exception) {
            throw e
        }
}

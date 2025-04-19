package com.example.animelist.data.api

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.GetMediaBySortTypeQuery
import com.example.MediaByIdQuery
import com.example.animelist.data.mapper.toAnimeDetails
import com.example.animelist.data.mapper.toMedia
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.model.MediaDetails
import com.example.animelist.domain.repository.SortType
import com.example.type.MediaSort
import com.example.type.MediaType

class ApolloMediaApi(
    private val apolloClient: ApolloClient
) : MediaApi {
    override suspend fun getTopMedia(page: Int, perPage: Int, type: com.example.animelist.domain.repository.MediaType, sortType: SortType): List<Media> {
        val mediaSort = when (sortType) {
            SortType.Popular -> MediaSort.POPULARITY_DESC
            SortType.Trending -> MediaSort.TRENDING_DESC
        }
        val mediaType = when (type) {
            com.example.animelist.domain.repository.MediaType.Anime -> MediaType.ANIME
            com.example.animelist.domain.repository.MediaType.Manga -> MediaType.MANGA
        }
        return apolloClient
            .query(query = GetMediaBySortTypeQuery(page = page, perPage = perPage, sort = listOf(mediaSort), type = mediaType))
            .execute()
            .data
            ?.Page
            ?.media
            ?.filterNotNull()
            ?.map { medium: GetMediaBySortTypeQuery.Medium -> medium.toMedia() }
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

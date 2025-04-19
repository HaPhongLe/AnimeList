package com.example.animelist.data.mapper

import com.example.GetMediaBySortTypeQuery
import com.example.animelist.domain.model.CoverImage
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.repository.MediaType

fun GetMediaBySortTypeQuery.Medium.toMedia(): Media {
    return Media(
        id = id,
        type = when (type) {
            com.example.type.MediaType.MANGA -> MediaType.Manga
            else -> MediaType.Anime
        },
        averageScore = averageScore,
        title = title?.toTitle(),
        coverImage = coverImage?.toCoverImage(),
        studios = studios?.toStudios() ?: emptyList(),
        genres = genres?.filterNotNull() ?: emptyList<String>()
    )
}

fun GetMediaBySortTypeQuery.Title.toTitle(): String? = english ?: userPreferred

fun GetMediaBySortTypeQuery.CoverImage.toCoverImage() = CoverImage(
    color = color,
    extraLarge = extraLarge,
    large = large
)

fun GetMediaBySortTypeQuery.Studios.toStudios(): List<String> {
    val studios = mutableListOf<String>()
    nodes?.filterNotNull()?.forEach { node ->
        studios.add(node.name)
    }
    return studios
}

package com.example.animelist.data.mapper

import com.example.GetAnimeBySortTypeQuery
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.model.CoverImage

fun GetAnimeBySortTypeQuery.Medium.toAnime(): Anime {
    return Anime(
        id = id,
        averageScore = averageScore,
        title = title?.toTitle(),
        coverImage = coverImage?.toCoverImage(),
        studios = studios?.toStudios() ?: emptyList(),
        genres = genres?.filterNotNull() ?: emptyList<String>()
    )
}

fun GetAnimeBySortTypeQuery.Title.toTitle(): String? = english ?: userPreferred

fun GetAnimeBySortTypeQuery.CoverImage.toCoverImage() = CoverImage(
    color = color,
    extraLarge = extraLarge,
    large = large
)

fun GetAnimeBySortTypeQuery.Studios.toStudios(): List<String> {
    val studios = mutableListOf<String>()
    nodes?.filterNotNull()?.forEach { node ->
        studios.add(node.name)
    }
    return studios
}

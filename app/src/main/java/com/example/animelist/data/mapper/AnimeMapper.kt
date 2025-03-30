package com.example.animelist.data.mapper

import com.example.TrendingAnimeQuery
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.model.CoverImage
import com.example.animelist.domain.model.Title

fun TrendingAnimeQuery.Medium.toAnime(): Anime {
    return Anime(
        id = id,
        averageScore = averageScore,
        title = title?.toTitle(),
        coverImage = coverImage?.toCoverImage(),
        studios = studios?.toStudios() ?: emptyList(),
        genres = genres?.filterNotNull() ?: emptyList<String>()
    )
}

fun TrendingAnimeQuery.Title.toTitle(): Title = Title(
    english = english
)

fun TrendingAnimeQuery.CoverImage.toCoverImage() = CoverImage(
    color = color,
    extraLarge = extraLarge,
    large = large
)

fun TrendingAnimeQuery.Studios.toStudios(): List<String> {
    val studios = mutableListOf<String>()
    nodes?.filterNotNull()?.forEach { node ->
        studios.add(node.name)
    }
    return studios
}

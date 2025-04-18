package com.example.animelist.data.mapper

import com.example.GetMangaBySortTypeQuery
import com.example.animelist.domain.model.CoverImage
import com.example.animelist.domain.model.Manga

fun GetMangaBySortTypeQuery.Medium.toManga(): Manga {
    return Manga(
        id = id,
        averageScore = averageScore,
        title = title?.toTitle(),
        coverImage = coverImage?.toCoverImage(),
        genres = genres?.filterNotNull() ?: emptyList<String>()
    )
}

fun GetMangaBySortTypeQuery.Title.toTitle(): String? = english ?: userPreferred

fun GetMangaBySortTypeQuery.CoverImage.toCoverImage() = CoverImage(
    color = color,
    extraLarge = extraLarge,
    large = large
)

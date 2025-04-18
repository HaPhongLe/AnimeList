package com.example.animelist.domain.mockModel

import com.example.animelist.domain.model.CoverImage
import com.example.animelist.domain.model.Manga

fun Manga.Companion.mock(
    id: Int = 1,
    averageScore: Int? = 85,
    title: String? = "Dandadan",
    coverImage: CoverImage? = CoverImage.mock(),
    genres: List<String> = listOf("Action")
) = Manga(
    id = id,
    averageScore = averageScore,
    title = title,
    coverImage = coverImage,
    genres = genres
)

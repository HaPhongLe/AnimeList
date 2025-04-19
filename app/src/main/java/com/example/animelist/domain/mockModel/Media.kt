package com.example.animelist.domain.mockModel

import com.example.animelist.domain.model.CoverImage
import com.example.animelist.domain.model.Media
import com.example.animelist.domain.repository.MediaType

fun Media.Companion.mock(
    id: Int = 1,
    type: MediaType = MediaType.Anime,
    averageScore: Int? = 85,
    title: String? = "Dandadan",
    coverImage: CoverImage? = CoverImage.mock(),
    studios: List<String> = listOf("8-bit"),
    genres: List<String> = listOf("Action")
) = Media(
    id = id,
    type = type,
    averageScore = averageScore,
    title = title,
    coverImage = coverImage,
    studios = studios,
    genres = genres
)

fun CoverImage.Companion.mock() = CoverImage(
    color = "#e47850",
    extraLarge = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/small/bx171018-2ldCj6QywuOa.jpg",
    large = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx171018-2ldCj6QywuOa.jpg"
)

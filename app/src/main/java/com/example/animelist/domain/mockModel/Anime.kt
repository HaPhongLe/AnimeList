package com.example.animelist.domain.mockModel

import com.example.animelist.domain.model.AiringSchedule
import com.example.animelist.domain.model.AiringScheduleNode
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.model.CoverImage
import com.example.animelist.domain.model.Ranking
import com.example.animelist.domain.model.Title

fun Anime.Companion.mock(
    averageScore: Int? = 85,
    episodes: Int? = 12,
    ranking: List<Ranking>? = listOf(Ranking.mock()),
    trending: Int? = 100000,
    title: Title? = Title.mock(),
    airingSchedule: AiringSchedule? = AiringSchedule.mock(),
    coverImage: CoverImage? = CoverImage.mock(),
    studios: List<String> = listOf("8-bit")
) = Anime(
    averageScore = averageScore,
    episodes = episodes,
    ranking = ranking,
    trending = trending,
    title = title,
    airingSchedule = airingSchedule,
    coverImage = coverImage,
    studios = studios
)

fun Ranking.Companion.mock(
    allTime: Boolean = false,
    year: Int = 2024,
    rank: Int = 1
) = Ranking(
    allTime = allTime,
    year = year,
    rank = rank
)

fun Title.Companion.mock(
    english: String = "Dandadan"
) = Title(
    english = english
)

fun AiringSchedule.Companion.mock(
    nodes: List<AiringScheduleNode> = listOf(AiringScheduleNode.mock())
) = AiringSchedule(
    nodes = nodes
)

fun AiringScheduleNode.Companion.mock(
    airingAt: Int = 12,
    episodes: Int = 12,
    id: Int = 1,
    timeUtilAiring: Int = 1
) = AiringScheduleNode(
    airingAt = airingAt,
    episodes = episodes,
    id = id,
    timeUtilAiring = timeUtilAiring
)

fun CoverImage.Companion.mock() = CoverImage(
    color = "#e47850",
    extraLarge = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/small/bx171018-2ldCj6QywuOa.jpg",
    large = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx171018-2ldCj6QywuOa.jpg"
)

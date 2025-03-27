package com.example.animelist.data.mapper

import com.example.TrendingAnimeQuery
import com.example.animelist.domain.model.AiringSchedule
import com.example.animelist.domain.model.AiringScheduleNode
import com.example.animelist.domain.model.Anime
import com.example.animelist.domain.model.CoverImage
import com.example.animelist.domain.model.Ranking
import com.example.animelist.domain.model.Title

fun TrendingAnimeQuery.Medium.toAnime(): Anime {
    return Anime(
        id = id,
        averageScore = averageScore,
        episodes = episodes,
        ranking = rankings?.filterNotNull()?.map { it.toRanking() },
        trending = trending,
        title = title?.toTitle(),
        airingSchedule = airingSchedule?.toAiringSchedule(),
        coverImage = coverImage?.toCoverImage(),
        studios = studios?.toStudios() ?: emptyList(),
        meanScore = meanScore,
        genres = genres?.filterNotNull() ?: emptyList<String>(),
        nexEpisodeAiring = nextAiringEpisode?.toAiringSchedule()
    )
}

fun TrendingAnimeQuery.Ranking.toRanking(): Ranking = Ranking(
    allTime = allTime,
    year = year,
    rank = rank
)

fun TrendingAnimeQuery.Title.toTitle(): Title = Title(
    english = english
)

fun TrendingAnimeQuery.AiringSchedule.toAiringSchedule() = AiringSchedule(
    nodes = nodes?.filterNotNull()?.map { it.toAiringScheduleNode() } ?: emptyList()
)

fun TrendingAnimeQuery.Node.toAiringScheduleNode() = AiringScheduleNode(
    airingAt = airingAt,
    episodes = episode,
    id = id,
    timeUtilAiring = timeUntilAiring
)

fun TrendingAnimeQuery.NextAiringEpisode.toAiringSchedule() = AiringScheduleNode(
    airingAt = airingAt,
    episodes = episode,
    id = id,
    timeUtilAiring = timeUntilAiring
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

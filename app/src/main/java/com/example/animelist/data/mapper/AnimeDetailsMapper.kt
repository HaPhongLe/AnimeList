package com.example.animelist.data.mapper

import com.example.AnimeByIdQuery
import com.example.animelist.domain.model.AiringSchedule
import com.example.animelist.domain.model.AiringScheduleNode
import com.example.animelist.domain.model.AnimeDetails
import com.example.animelist.domain.model.DateInfo
import com.example.animelist.domain.model.StreamingEpisode

fun AnimeByIdQuery.Media.toAnimeDetails() = AnimeDetails(
    bannerImage = bannerImage,
    title = title?.toTitle(),
    genres = genres?.filterNotNull() ?: emptyList<String>(),
    airingSchedule = airingSchedule?.toAiringSchedule(),
    averageScore = averageScore,
    description = description
        ?.replace("<br>", "\n")
        ?.replace(Regex("<[^>]*>"), ""),
    endDate = endDate?.toDateInfo(),
    startDate = startDate?.toDateInfo(),
    status = status.toString(),
    episodes = episodes,
    streamingEpisodes = streamingEpisodes?.filterNotNull()?.map { it.toStreamingEpisode() } ?: emptyList()
)

fun AnimeByIdQuery.Title.toTitle(): String? = english ?: userPreferred

fun AnimeByIdQuery.AiringSchedule.toAiringSchedule() = AiringSchedule(
    nodes = nodes?.filterNotNull()?.map { it.toAiringScheduleNode() } ?: emptyList()
)

fun AnimeByIdQuery.Node.toAiringScheduleNode() = AiringScheduleNode(
    airingAt = airingAt,
    episodes = episode,
    id = id,
    timeUtilAiring = timeUntilAiring
)

fun AnimeByIdQuery.EndDate.toDateInfo() = DateInfo(
    day = day,
    month = month,
    year = year
)

fun AnimeByIdQuery.StartDate.toDateInfo() = DateInfo(
    day = day,
    month = month,
    year = year
)

fun AnimeByIdQuery.StreamingEpisode.toStreamingEpisode() = StreamingEpisode(
    site = site,
    url = url,
    title = title,
    thumbnail = thumbnail
)

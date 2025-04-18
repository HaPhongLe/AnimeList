package com.example.animelist.data.mapper

import com.example.MediaByIdQuery
import com.example.animelist.domain.model.AiringSchedule
import com.example.animelist.domain.model.AiringScheduleNode
import com.example.animelist.domain.model.DateInfo
import com.example.animelist.domain.model.MediaDetails
import com.example.animelist.domain.model.StreamingEpisode

fun MediaByIdQuery.Media.toAnimeDetails() = MediaDetails(
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

fun MediaByIdQuery.Title.toTitle(): String? = english ?: userPreferred

fun MediaByIdQuery.AiringSchedule.toAiringSchedule() = AiringSchedule(
    nodes = nodes?.filterNotNull()?.map { it.toAiringScheduleNode() } ?: emptyList()
)

fun MediaByIdQuery.Node.toAiringScheduleNode() = AiringScheduleNode(
    airingAt = airingAt,
    episodes = episode,
    id = id,
    timeUtilAiring = timeUntilAiring
)

fun MediaByIdQuery.EndDate.toDateInfo() = DateInfo(
    day = day,
    month = month,
    year = year
)

fun MediaByIdQuery.StartDate.toDateInfo() = DateInfo(
    day = day,
    month = month,
    year = year
)

fun MediaByIdQuery.StreamingEpisode.toStreamingEpisode() = StreamingEpisode(
    site = site,
    url = url,
    title = title,
    thumbnail = thumbnail
)

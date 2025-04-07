package com.example.animelist.domain.mockModel

import com.example.animelist.domain.model.AiringSchedule
import com.example.animelist.domain.model.AiringScheduleNode
import com.example.animelist.domain.model.AnimeDetails
import com.example.animelist.domain.model.DateInfo

fun AnimeDetails.Companion.mock(
    bannerImage: String? = "https://s4.anilist.co/file/anilistcdn/media/anime/banner/1-OquNCNB6srGe.jpg",
    title: String? = "Cowboy Bebop",
    genres: List<String>? = listOf(
        "Action",
        "Adventure",
        "Drama",
        "Sci-Fi"
    ),
    airingSchedule: AiringSchedule? = AiringSchedule.mock(),
    averageScore: Int? = 86,
    description: String? = "Enter a world in the distant future, where Bounty Hunters roam the solar system. Spike and Jet, bounty hunting partners, set out on journeys in an ever struggling effort to win bounty rewards to survive.<br><br>\nWhile traveling, they meet up with other very interesting people. Could Faye, the beautiful and ridiculously poor gambler, Edward, the computer genius, and Ein, the engineered dog be a good addition to the group?",
    endDate: DateInfo? = DateInfo.mock(),
    startDate: DateInfo? = DateInfo.mock(day = 3, month = 4, year = 1998),
    status: String? = "FINISHED"
) = AnimeDetails(
    bannerImage = bannerImage,
    title = title,
    genres = genres,
    airingSchedule = airingSchedule,
    averageScore = averageScore,
    description = description,
    endDate = endDate,
    startDate = startDate,
    status = status
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

fun DateInfo.Companion.mock(
    day: Int? = 24,
    month: Int? = 4,
    year: Int? = 1999
) = DateInfo(day = day, month = month, year = year)

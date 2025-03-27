package com.example.animelist.domain.model

data class Anime(
    val id: Int,
    val averageScore: Int?,
    val episodes: Int?,
    val ranking: List<Ranking>?,
    val trending: Int?,
    val title: Title?,
    val airingSchedule: AiringSchedule?,
    val coverImage: CoverImage?,
    val studios: List<String>,
    val meanScore: Int?,
    val genres: List<String>,
    val nexEpisodeAiring: AiringScheduleNode?
) {
    companion object Companion
}

data class Ranking(
    val allTime: Boolean?,
    val year: Int?,
    val rank: Int
) {
    companion object Companion
}

data class Title(
    val english: String?
) {
    companion object Companion
}

data class AiringSchedule(
    val nodes: List<AiringScheduleNode>
) {
    companion object Companion
}

data class AiringScheduleNode(
    val airingAt: Int,
    val episodes: Int,
    val id: Int,
    val timeUtilAiring: Int
) {
    companion object Companion
}

data class CoverImage(
    val color: String?,
    val large: String?,
    val extraLarge: String?
) {
    companion object Companion
}

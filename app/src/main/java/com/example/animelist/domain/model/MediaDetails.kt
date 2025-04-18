package com.example.animelist.domain.model

data class MediaDetails(
    val bannerImage: String?,
    val title: String?,
    val genres: List<String>?,
    val airingSchedule: AiringSchedule?,
    val averageScore: Int?,
    val description: String?,
    val endDate: DateInfo?,
    val startDate: DateInfo?,
    val status: String?,
    val episodes: Int?,
    val streamingEpisodes: List<StreamingEpisode>
) {
    companion object Companion
}

data class DateInfo(
    val day: Int?,
    val month: Int?,
    val year: Int?
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

data class StreamingEpisode(
    val site: String?,
    val url: String?,
    val title: String?,
    val thumbnail: String?
) {
    companion object Companion
}

package com.example.animelist.domain.model

data class Anime(
    val id: Int,
    val averageScore: Int?,
    val title: String?,
    val coverImage: CoverImage?,
    val studios: List<String>,
    val genres: List<String>
) {
    companion object Companion
}

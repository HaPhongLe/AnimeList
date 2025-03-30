package com.example.animelist.domain.model

data class Anime(
    val id: Int,
    val averageScore: Int?,
    val title: Title?,
    val coverImage: CoverImage?,
    val studios: List<String>,
    val genres: List<String>
) {
    companion object Companion
}

data class Title(
    val english: String?
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

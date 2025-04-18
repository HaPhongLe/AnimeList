package com.example.animelist.domain.model

data class Manga(
    val id: Int,
    val averageScore: Int?,
    val title: String?,
    val coverImage: CoverImage?,
    val genres: List<String>
) {
    companion object Companion
}

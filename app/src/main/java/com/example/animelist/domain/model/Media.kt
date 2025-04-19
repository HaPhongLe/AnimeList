package com.example.animelist.domain.model

import com.example.animelist.domain.repository.MediaType

data class Media(
    val id: Int,
    val type: MediaType,
    val averageScore: Int?,
    val title: String?,
    val coverImage: CoverImage?,
    val studios: List<String>,
    val genres: List<String>
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

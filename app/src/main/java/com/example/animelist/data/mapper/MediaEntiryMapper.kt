package com.example.animelist.data.mapper

import com.example.animelist.data.local.media.MediaEntity
import com.example.animelist.domain.model.Media

fun MediaEntity.toDomain() = Media(
    id = id,
    type = type,
    averageScore = averageScore,
    title = title,
    coverImage = coverImage,
    studios = studios,
    genres = genres
)

fun Media.toEntity() = MediaEntity(
    id = id,
    type = type,
    averageScore = averageScore,
    title = title,
    coverImage = coverImage,
    studios = studios,
    genres = genres
)

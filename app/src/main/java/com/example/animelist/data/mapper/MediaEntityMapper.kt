package com.example.animelist.data.mapper

import com.example.animelist.data.local.media.MediaEntity
import com.example.animelist.domain.model.Media

fun Media.toEntity() = MediaEntity(
    id = id
)

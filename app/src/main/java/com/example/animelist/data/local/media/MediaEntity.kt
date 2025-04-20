package com.example.animelist.data.local.media

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.animelist.domain.model.CoverImage
import com.example.animelist.domain.repository.MediaType

@Entity(tableName = "media")
data class MediaEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "type") val type: MediaType,
    @ColumnInfo(name = "averageScore") val averageScore: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "cover_image") val coverImage: CoverImage?,
    @ColumnInfo(name = "studios") val studios: List<String>,
    @ColumnInfo(name = "genres") val genres: List<String>
)

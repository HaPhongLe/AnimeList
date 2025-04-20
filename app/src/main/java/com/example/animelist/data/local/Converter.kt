package com.example.animelist.data.local

import androidx.room.TypeConverter
import com.example.animelist.domain.model.CoverImage
import com.example.animelist.domain.repository.MediaType
import com.google.gson.Gson

class Converter {
    @TypeConverter
    fun fromMediaType(type: MediaType): String = type.name

    @TypeConverter
    fun toMediaType(mediaTypeName: String): MediaType = when (mediaTypeName) {
        MediaType.Anime.name -> MediaType.Anime
        else -> MediaType.Manga
    }

    @TypeConverter
    fun fromCoverImage(coverImage: CoverImage): String = Gson().toJson(coverImage)

    @TypeConverter
    fun toCoverImage(json: String): CoverImage = Gson().fromJson(json, CoverImage::class.java)

    @TypeConverter
    fun fromStudios(studios: List<String>): String = studios.joinToString(",")

    @TypeConverter
    fun toStudios(data: String): List<String> = data.split(",")
}

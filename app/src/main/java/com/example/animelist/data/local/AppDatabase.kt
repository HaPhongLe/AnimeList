package com.example.animelist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.animelist.data.local.media.MediaDao
import com.example.animelist.data.local.media.MediaEntity

@Database(entities = [MediaEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
}

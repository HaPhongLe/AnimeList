package com.example.animelist.data.local.media

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {
    @Query("SELECT * FROM media")
    fun getAll(): Flow<List<MediaEntity>>

    @Query("SELECT COUNT(*) FROM media WHERE id = :id")
    fun countMediaEntityById(id: Int): Flow<Int>

    @Insert
    suspend fun insert(mediaEntity: MediaEntity)

    @Delete
    suspend fun delete(mediaEntity: MediaEntity)
}

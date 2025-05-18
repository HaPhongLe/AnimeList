package com.example.animelist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.animelist.data.local.media.MediaDao
import com.example.animelist.data.local.media.MediaEntity

@Database(entities = [MediaEntity::class], version = 2)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val TABLE_NAME = "app_database"
    }
    abstract fun mediaDao(): MediaDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE media_new (id INTEGER PRIMARY KEY NOT NULL)")
        db.execSQL("INSERT INTO media_new SELECT id FROM media")
        db.execSQL("DROP TABLE media")
        db.execSQL("ALTER TABLE media_new RENAME TO media")
    }
}

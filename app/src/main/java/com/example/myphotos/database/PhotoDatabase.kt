package com.example.myphotos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.schoolorgonizer.notes.database.PhotoEntity


@Database(entities = [NotePhotoEntity::class, PhotoEntity::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
    abstract fun noteDao(): NoteDao
}

object DatabaseConstructor {
    fun create(context: Context): PhotoDatabase =
        Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            "photo_database"
        ).build()
}
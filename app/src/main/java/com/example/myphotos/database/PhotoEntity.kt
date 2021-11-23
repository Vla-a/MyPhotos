package com.example.schoolorgonizer.notes.database

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "photo_table", indices = [Index(value = ["url"], unique = true)])
class PhotoEntity(

    val id: Long,
    @PrimaryKey
    @ColumnInfo(name = "url")
    val url: String,
    val date: String,
    val lat: Double,
    val lng: Double
)


